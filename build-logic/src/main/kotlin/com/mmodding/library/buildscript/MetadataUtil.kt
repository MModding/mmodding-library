package com.mmodding.library.buildscript

import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.artifacts.VersionConstraint
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.getByType

fun Project.rootLibs() : VersionCatalog {
    return rootProject.extensions.getByType<VersionCatalogsExtension>().named("libs")
}

fun Project.catalogedVersion(alias: String) : String {
    return (rootLibs().findVersion(alias).orElseThrow() as VersionConstraint).requiredVersion
}

fun Project.catalogedLibrary(alias: String) : Provider<MinimalExternalModuleDependency> {
    return rootLibs().findLibrary(alias).orElseThrow() as Provider<MinimalExternalModuleDependency>
}

fun Project.flattenedMinecraftVersion() : String {
    var mcVer = catalogedVersion("minecraft")
    if (mcVer.contains("-pre") || mcVer.contains("-rc")) {
        mcVer = mcVer.split("-").first()
    }
    return mcVer
}

fun Project.getModuleNamespace() : String {
    return if (project.rootProject == project) "mmodding"
    else project.name.replace('-', '_')
}

fun Project.getModuleName() : String {
    var projectName = ""
    getModuleNamespace().split("_").forEach { sub ->
        projectName += if (sub[0] == 'm' && sub[1] == 'm') {
            sub[0].uppercase() + sub[1].uppercase() + sub.substring(2) + " "
        } else {
            projectName + sub[0].uppercase() + sub.substring(1) + " "
        }
    }
    return if (!projectName.contains("Mod Integration")) projectName + "Library"
    else projectName.substring(0, projectName.length - 1)
}

fun Project.getModuleDescription() : String {
    return if (getModuleNamespace() == "mmodding") "Library made by MModding Team to provide few sets of modding tools."
    else getModuleName().substring(9) + " of MModding"
}
