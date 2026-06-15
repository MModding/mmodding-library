package com.mmodding.library

plugins {
    id("com.mmodding.library.project-meta")
}

afterEvaluate {
    // Disable the gen sources task on integration modules
    tasks.named("genSourcesWithVineflower") { enabled = false }
    tasks.named("genSourcesWithCfr") { enabled = false }
}
