pluginManagement {
    repositories {
		maven {
			name = "JitPack"
			url = uri("https://maven.mmodding.com/releases")
		}
		maven {
            name = "Quilt"
            url = uri("https://maven.quiltmc.org/repository/release")
        }
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
        gradlePluginPortal()
    }
    includeBuild("./build-logic")
}

rootProject.name = "mmodding-library"

include("mmodding-block")
include("mmodding-client-resources")
include("mmodding-config")
include("mmodding-core")
include("mmodding-datagen")
include("mmodding-enchantment")
include("mmodding-fluid")
include("mmodding-inventory")
include("mmodding-item")
include("mmodding-java")
include("mmodding-math")
include("mmodding-network")
include("mmodding-portal")
include("mmodding-sublevel")
include("mmodding-task")
include("mmodding-woodset")
include("mmodding-worldgen")
rootDir.toPath().resolve("mod-integration").toFile().list().forEach { suffix ->
    include("mod-integration-mmodding-$suffix")
    project(":mod-integration-mmodding-$suffix").projectDir = file("mod-integration/$suffix")
}