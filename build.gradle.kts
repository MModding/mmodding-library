import com.mmodding.library.buildscript.*

plugins {
    id("maven-publish")
	id("com.mmodding.library.project-meta")
}

group = project.properties["maven_group"] as String

val supportedIntegrations = (project.properties["allowed_integrations"] as String).split(",")

mmodding {
	modules {
		bundle("mmodding-block")
		bundle("mmodding-client-resources")
		bundle("mmodding-config")
		bundle("mmodding-core")
		bundle("mmodding-datagen")
		bundle("mmodding-enchantment")
		bundle("mmodding-fluid")
		bundle("mmodding-inventory")
		bundle("mmodding-item")
		bundle("mmodding-java")
		bundle("mmodding-math")
		bundle("mmodding-network")
		bundle("mmodding-portal")
		bundle("mmodding-sublevel")
		bundle("mmodding-task")
		bundle("mmodding-woodset")
		bundle("mmodding-worldgen")
		rootDir.toPath().resolve("mod-integration").toFile().list().forEach { suffix ->
			if (supportedIntegrations.contains(suffix)) {
				include("mod-integration-mmodding-$suffix") // we don't need to depend on it for the root project
			}
		}
	}
}

dependencies {
	api(include(libs.yumi.commons.core.get())!!)
	api(include(libs.yumi.commons.collections.get())!!)
	api(include(libs.yumi.commons.event.get())!!)
}

// Javadocs (we're basically doing the same as FAPI here)
tasks.named<Javadoc>("javadoc") {
	enabled = true

	options {
		this as StandardJavadocDocletOptions
		source = catalogedVersion("java")
		encoding = "UTF-8"
		charset("UTF-8")
		memberLevel = JavadocMemberLevel.PACKAGE
		tags = listOf("apiNote:a:API Note:", "implNote:a:Implementation Note:")
		addStringOption("Xdoclint:all,-missing", "-quiet")
	}

	subprojects.forEach { p ->
		if (p.plugins.hasPlugin("com.mmodding.library.module")) {
			source(p.sourceSets.main.get().allJava)
		}
	}
	classpath = sourceSets.main.get().compileClasspath
	include("**/api/**")
	isFailOnError = true
}

tasks.register<Jar>("javadocJar") {
    description = "Bundling Javadoc Jar"
	dependsOn(tasks.named("javadoc"))
	from(tasks.named<Javadoc>("javadoc").get().destinationDir)
	archiveClassifier = "fatjavadoc"
}

tasks.named("build").get().dependsOn(tasks.named("javadocJar"))

// This file prevents javadoc generation from failing because of javadoc compile-time issues
loom.accessWidenerPath = file("gradle/javadoc.classtweaker")

// Configure the maven publication
publishing {
	publications {
		create<MavenPublication>("mavenJava") {
			from(components["java"])
			artifact(tasks.named("javadocJar"))
		}
	}

	repositories {
		if (providers.environmentVariable("MAVEN_USERNAME").isPresent) {
			maven {
				name = "MModding Maven Repository"
				url = uri("https://maven.mmodding.com/releases")
				credentials {
					username = providers.environmentVariable("MAVEN_USERNAME").get()
					password = providers.environmentVariable("MAVEN_PASSWORD").get()
				}
			}
		}
	}
}
