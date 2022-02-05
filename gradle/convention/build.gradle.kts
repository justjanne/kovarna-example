plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
    maven(url = "https://maven.minecraftforge.net/")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    implementation("net.minecraftforge.gradle:ForgeGradle:5.1.+")
}
