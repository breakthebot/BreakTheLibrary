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
    compileOnly("org.slf4j:slf4j-api:2.0.9")
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
