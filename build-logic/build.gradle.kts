plugins {
    `kotlin-dsl`
}

repositories {
    maven ("https://maven.mmodding.com/releases")
    maven ("https://maven.fabricmc.net/")
    maven ("https://maven.quiltmc.org/repository/release")
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(libs.fabric.loom)
    implementation(libs.mmodding.gradle)
}