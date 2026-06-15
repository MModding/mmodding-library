package com.mmodding.library

import com.mmodding.library.buildscript.*

plugins {
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
