//import proguard.gradle.ProGuardTask
// TODO: migrate from proguard to r8

plugins {
    kotlin("jvm") version "2.1.21"
    kotlin("plugin.serialization") version "1.9.10"
    `maven-publish`
}

group = "org.breakthebot"
version = "1.0.0"

repositories {
    mavenCentral()
}

//buildscript {
//    repositories {
//        mavenCentral()
//    }
//    dependencies {
//        classpath("com.guardsquare:proguard-gradle:7.5.0")
//    }
//}

// val isRelease = project.hasProperty("release")
val shouldPublish = project.hasProperty("publish")


java {
    withSourcesJar()
}

val ktSerde = project.property("kt_serialisation_json")
val ktCoroutines = project.property("kt_coroutines")
val slf4j = project.property("sl4j_version")

dependencies {
    // tests
    testImplementation(kotlin("test"))
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:$ktSerde")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$ktCoroutines")

    // deps
    compileOnly("org.slf4j:slf4j-api:$slf4j")
}

//tasks.register<ProGuardTask>("obfuscate") {
//
//    dependsOn(tasks.named<Jar>("jar"))
//
//    injars(tasks.named<Jar>("jar").flatMap { it.archiveFile })
//
//    outjars(layout.buildDirectory.file("libs/${project.name}-${project.version}-obf.jar"))
//
//    libraryjars("${System.getProperty("java.home")}/jmods/java.base.jmod")
//
//    configuration("./proguard-rules.pro")
//}
val headerText = file("header.txt").readText()

val addHeader by tasks.registering {
    group = "build"

    val targetFiles = fileTree("src") {
        include("**/*.kt")
    }

    doLast {
        targetFiles.forEach { file: File ->
            val content = file.readText()
            if (!content.startsWith(headerText)) {
                file.writeText("$headerText\n$content")
            }
        }
    }
}

tasks.named("compileKotlin") {
    dependsOn(addHeader)
}


tasks.test {
    useJUnitPlatform()
}

//tasks.named("build") {
//    if (isRelease) {
//        dependsOn("obfuscate")
//    }
//}

tasks.withType<PublishToMavenRepository>().configureEach {
    onlyIf { shouldPublish }
}

kotlin {
    jvmToolchain(21)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            val obfJar = layout.buildDirectory.file("libs/${project.name}-${project.version}-obf.jar")

            artifact(obfJar) {
                builtBy(tasks.named("build"))
            }

            groupId = project.group.toString()
            artifactId = "breakthelibrary"
            version = project.version.toString()
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
