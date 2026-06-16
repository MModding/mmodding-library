import com.mmodding.library.buildscript.*

plugins {
    id("maven-publish")
	id("me.modmuss50.mod-publish-plugin") version "2.0.0"
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
		bundle("mmodding-levelgen")
		bundle("mmodding-math")
		bundle("mmodding-network")
		bundle("mmodding-portal")
		bundle("mmodding-sublevel")
		bundle("mmodding-task")
		bundle("mmodding-woodset")
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

// Configures the maven publication
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
				name = "MModding"
				url = uri("https://maven.mmodding.com/releases")
				credentials {
					username = providers.environmentVariable("MAVEN_USERNAME").get()
					password = providers.environmentVariable("MAVEN_PASSWORD").get()
				}
			}
		}
	}
}

fun extractSupportedVersions() : List<String> {
	var mcVer = catalogedVersion("minecraft")
	if (mcVer.contains("snapshot")) {
		return listOf(mcVer)
	}
	else {
		// published artifacts on these versions should also cover the proper Minecraft release correctly
		if (mcVer.contains("-pre")) mcVer = mcVer.split("-pre").first()
		if (mcVer.contains("-rc")) mcVer = mcVer.split("-rc").first()
		val versionComponents = mcVer.split(".")
		if (versionComponents.size == 2) {
			return listOf(mcVer)
		}
		else {
			val lastComponent = versionComponents.last().toInt()
			val versionBase = versionComponents.subList(0, 1).joinToString(".")
			val versions = mutableListOf(versionBase)
			for (i in 1..lastComponent) {
				versions.add("$versionBase.$i")
			}
			return versions
		}
	}
}

// Configures the mod publication
publishMods {
	if (providers.environmentVariable("CHANGELOG").isPresent) {
		displayName = "${properties["mod_name"]} ${project.version}"
		changelog.set(providers.environmentVariable("CHANGELOG").get())

		val title = providers.environmentVariable("TITLE").get()
        if (title.contains("alpha")) {
			type.set(ALPHA)
		}
        else if (title.contains("beta")) {
			type.set(BETA)
		}
		else {
			type.set(STABLE)
		}

		file.set(tasks.named<Jar>("jar").get().archiveFile)
        additionalFiles.from(tasks.named<Jar>("sourcesJar").get().archiveFile, tasks.named<Jar>("javadocJar").get().archiveFile)

		modLoaders.add("fabric")
		modLoaders.add("quilt")

		modrinth {
			projectId = project.properties["modrinth_project"] as String
			accessToken = providers.environmentVariable("MODRINTH_TOKEN").get()

			minecraftVersions = extractSupportedVersions()

			projectDescription = providers.fileContents(layout.projectDirectory.file("README.md")).asText

			requires("fabric-api")
		}

		curseforge {
			projectId = project.properties["curseforge_project"] as String
			accessToken = providers.environmentVariable("CURSEFORGE_TOKEN").get()

			javaVersions.add(JavaVersion.entries.first { v -> v.name == "VERSION_" + catalogedVersion("java") })

			minecraftVersions = extractSupportedVersions()

			client = true
			server = true

			changelogType = "markdown"

			requires("fabric-api")
		}
	}
}
