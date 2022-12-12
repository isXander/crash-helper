import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    application
    kotlin("jvm")
    kotlin("plugin.serialization")
}

repositories {
    maven("https://maven.kotlindiscord.com/repository/maven-public/") { name = "Kotlin Extensions" }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation(project(path = ":base", configuration = "default"))

    implementation(libs.kord.core)
    implementation(libs.kord.extensions)

    implementation(libs.logback.classic)
    implementation(libs.kotlin.logging)
    implementation(libs.groovy)
}

application {
    mainClass.set("dev.isxander.crashhelper.bot.AppKt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}
