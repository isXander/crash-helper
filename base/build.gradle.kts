plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    java
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(libs.gson)
}
