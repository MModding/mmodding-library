package com.mmodding.library

plugins {
    id("maven-publish")
    id("com.mmodding.library.project-meta")
}

group = "${project.properties["maven_group"] as String}.mmodding-library"

afterEvaluate {
    // Disable the gen sources task on library modules
    tasks.named("genSourcesWithVineflower") { enabled = false }
    tasks.named("genSourcesWithCfr") { enabled = false }
}

tasks.named("javadoc") {
    enabled = false
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
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
