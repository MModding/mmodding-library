package com.mmodding.library

import com.mmodding.library.buildscript.*

plugins {
    id("com.mmodding.library.project-meta")
}

group = project.properties["module_maven_group"] as String

afterEvaluate {
    // Disable the gen sources task on library modules
    tasks.named("genSourcesWithVineflower") { enabled = false }
    tasks.named("genSourcesWithCfr") { enabled = false }
}