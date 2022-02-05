enableFeaturePreview("VERSION_CATALOGS")

rootProject.name = "examplemod"

includeBuild("gradle/convention")

include("mod")

pluginManagement {
  repositories {
    gradlePluginPortal()
    mavenCentral()
    maven(url = "https://maven.minecraftforge.net/")
    mavenLocal()
  }

  plugins {
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
    id("org.jetbrains.dokka") version "1.6.10"
  }
}
