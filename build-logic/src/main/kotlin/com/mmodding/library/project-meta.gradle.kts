package com.mmodding.library

import com.mmodding.gradle.api.EnvironmentTarget
import com.mmodding.gradle.api.mod.json.NamespaceProvider
import com.mmodding.library.buildscript.*
import org.gradle.api.tasks.compile.JavaCompile

plugins {
    id("net.fabricmc.fabric-loom")
    id("com.mmodding.gradle")
}

val baseVersion = project.properties["version"]
version = "$baseVersion+${catalogedVersion("minecraft")}"

fun provideNamespaceAlternatives(provider: NamespaceProvider) {
    val projectId = getModuleNamespace();
    if (projectId != "mmodding") {
        provider.provide(projectId.replace("_", "-"))
    }
    if (!projectId.contains("mod_integration")) {
        provider.provide(projectId + "_library")
        provider.provide(projectId.replace("_", "-") + "-library")
        provider.provide(projectId + "_api")
        provider.provide(projectId.replace("_", "-") + "-api")
    }
}

base {
    archivesName = if (project == rootProject) project.properties["archives_base_name"] as String else getModuleNamespace()
}

repositories {
    mavenCentral()
}

dependencies {
    minecraft(catalogedLibrary("minecraft"))
    implementation(catalogedLibrary("fabric_loader"))

    implementation(catalogedLibrary("yumi_commons"))
    implementation(catalogedLibrary("fabric_api"))
}

tasks.named<ProcessResources>("processResources") {
    inputs.property("version", version)

    filesMatching("fabric.mod.json") {
        expand("version" to version)
    }
}

java {
    // Still required by IDEs such as Eclipse and Visual Studio Code
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25

    withSourcesJar()
    withJavadocJar()
}

tasks.withType<JavaCompile>().forEach { task ->
    task.options.encoding = "UTF-8"
    // Minecraft 26.1 upwards uses Java 25.
    task.options.release = catalogedVersion("java").toInt()
}

mmodding {
    configureFabricModJson {
        name = getModuleName()
        namespace = getModuleNamespace()
        description = getModuleDescription()
        license = "Code: PolyForm-Shield-1.0.0\\nAssets: All Rights Reserved"
        icon = "assets/" + getModuleNamespace() + "/icon.png"
        environment = EnvironmentTarget.ANY
        addAuthor("MModding Team")
        addContributor("FirstMegaGame4")
        withContact {
            homepage = "https://mmodding.com"
            sources = "https://github.com/MModding/mmodding-library"
            issues = "https://github.com/MModding/mmodding-library/issues"
        }
        withDependencies {
            javaVersion = ">=" + catalogedVersion("java")
            val minecraftVer = catalogedVersion("java")
            if (minecraftVer.contains("snapshot")) {
                val mcv = minecraftVer.split("-")[0]
                minecraftVersion = ">=${mcv}- <${mcv}"
            }
            else {
                minecraftVersion = "~$minecraftVer"
            }
            fabricLoaderVersion = ">=" + catalogedVersion("fabric_loader")
            fabricApiVersion = ">=" + catalogedVersion("fabric_api")
        }
        withProvider {
            provideNamespaceAlternatives(this)
        }
        if (getModuleNamespace() != "mmodding") withParent("mmodding")
        withCustom {
            withBlock("modmenu") {
                withArray("badges") {
                    addUnique("library")
                }
            }
        }
    }
    loomModRegistration()
    loomTestmodRegistration()
    configureTestmod {
        name = getModuleName() + " Test Mod"
        namespace = getModuleNamespace() + "_testmod"
        withDependencies {
            addDependency(getModuleNamespace())
        }
    }
}

tasks.named<Jar>("jar") {
    from(rootProject.projectDir.path + "/LICENSE.md") {
        rename { "${it}_${base.archivesName.get()}" }
    }
    if (project != rootProject) {
        from(rootProject.projectDir.path + "/src/main/resources/assets/mmodding/icon.png") {
            into("assets/" + getModuleNamespace())
        }
    }
}