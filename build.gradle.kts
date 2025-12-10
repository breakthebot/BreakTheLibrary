plugins {
    kotlin("jvm") version "2.2.20"
    kotlin("plugin.serialization") version "1.9.10"
}

group = "org.breakthebot"
version = "1.0"

repositories {
    mavenCentral()
}

java {
    withSourcesJar()
}

dependencies {
    testImplementation(kotlin("test"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${project.property("kt_serialisation_json")}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${project.property("kt_coroutines")}")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}