import com.mmodding.library.buildscript.*

plugins {
    id("maven-publish")
	id("com.mmodding.library.project-meta")
}

group = project.properties["maven_group"] as String

val included_integrations = (project.properties["allowed_integrations"] as String).split(",")

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
			if (included_integrations.contains(suffix)) {
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

// Javadocs
/* javadoc {
	options {
		source = "17"
		encoding = "UTF-8"
		charSet = "UTF-8"
		memberLevel = JavadocMemberLevel.PACKAGE
		addStringOption("Xdoclint:none", "-quiet")
		tags(
			'apiNote:a:API Note:',
			'implNote:a:Implementation Note:'
		)
	}

	allprojects.each {
		source(sourceSets.main.allJava)
	}

	classpath = files(sourceSets.main.compileClasspath)
	include("**//*api*//**")
	failOnError = true
} */

// Configure the maven publication
/* allprojects {
	if (!project.name.contains("mod-integration")) {
		apply plugin: 'maven-publish'

		afterEvaluate {
			publishing {
				publications {
					mavenJava(MavenPublication) {
						from components.java
					}
				}

				repositories {
					maven {
						name = "MModding Maven Repository"
						url = "https://maven.mmodding.com/releases"
						credentials {
							username = providers.environmentVariable("MAVEN_USERNAME")
							password = providers.environmentVariable("MAVEN_PASSWORD")
						}
					}
				}
			}
		}
	}
}
 */