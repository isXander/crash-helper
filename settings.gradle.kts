pluginManagement {
    plugins {
        kotlin("jvm") version "1.7.20"
        kotlin("plugin.serialization") version "1.7.20"
    }

    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net") { name = "FabricMC" }
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs")
    }
}

rootProject.name = "crash-helper"

include("base")
include("bot")
