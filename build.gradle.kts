import proguard.gradle.ProGuardTask

plugins {
    kotlin("jvm") version "2.2.20"
    kotlin("plugin.serialization") version "1.9.10"
    `maven-publish`
}

group = "org.breakthebot"
version = "1.0.0"

repositories {
    mavenCentral()
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("com.guardsquare:proguard-gradle:7.4.2")
    }
}


java {
    withSourcesJar()
}

val ktSerde = project.property("kt_serialisation_json")
val ktCoroutines = project.property("kt_coroutines")

dependencies {
    testImplementation(kotlin("test"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$ktSerde")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$ktCoroutines")
    implementation("com.guardsquare:proguard-base:7.4.2")

    compileOnly("org.slf4j:slf4j-api:2.0.9")
}

tasks.register<ProGuardTask>("obfuscate") {

    dependsOn(tasks.named<Jar>("jar"))

    injars(tasks.named<Jar>("jar").flatMap { it.archiveFile })

    outjars(layout.buildDirectory.file("libs/${project.name}-${project.version}-obf.jar"))

    libraryjars("${System.getProperty("java.home")}/jmods/java.base.jmod")

    configuration("proguard-rules.pro")
}


tasks.test {
    useJUnitPlatform()
}


kotlin {
    jvmToolchain(21)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = groupId
            artifactId = "breakthelibrary"
            version = version
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/breakthebot/breakthelibrary")

            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_USERNAME")
                password = project.findProperty("gpr.token") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
