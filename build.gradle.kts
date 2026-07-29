// import proguard.gradle.ProGuardTask
// TODO: migrate from proguard to r8

plugins {
    kotlin("jvm") version "2.4.10"
    kotlin("plugin.serialization") version "2.4.10"
    `maven-publish`
}

group = "org.breakthebot"
version = "1.6.13"

repositories {
    mavenCentral()
}

val shouldPublish = project.hasProperty("publish")

java {
    withSourcesJar()
    withJavadocJar()
}

val ktSerde = project.property("kt_serialisation_json")
val ktCoroutines = project.property("kt_coroutines")

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
}

val headerText = file("header.txt").readText()

val addHeader by tasks.registering {
    group = "build"

    val targetFiles =
        fileTree("src") {
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

tasks.withType<PublishToMavenRepository>().configureEach {
    onlyIf { shouldPublish }
}

kotlin {
    jvmToolchain(21)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

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
