val exposedVersion: String by project
val kotestVersion: String by project
val logbackVersion: String by project
val postgresVersion: String by project

plugins {
    kotlin("jvm") version "1.9.0"
    kotlin("plugin.serialization") version "1.9.0"
    `maven-publish`
    signing
    application
}

group = "net.lusade"
version = "0.1.0"
description = "ULID support for Exposed"

repositories {
    mavenCentral()
}

dependencies {
    api("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    compileOnly("org.postgresql:postgresql:$postgresVersion")
    testImplementation("ch.qos.logback:logback-classic:$logbackVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    testImplementation("org.postgresql:postgresql:$postgresVersion")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            afterEvaluate {
                artifactId = tasks.jar.get().archiveBaseName.get()
            }

            pom {
                name.set(rootProject.name)
                description.set(project.description)
                url.set("https://github.com/luluvia/ulid-exposed")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("luluvia")
                        name.set("Luis Luvia")
                        email.set("lluvia@lusade.net")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/luluvia/ulid-exposed.git")
                    developerConnection.set("scm:git:ssh://github.com/luluvia/ulid-exposed.git")
                    url.set("https://github.com/luluvia/ulid-exposed")
                }
            }
        }
    }

    repositories {
        maven {
            val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            val nexusUsername = System.getenv("NEXUS_USERNAME")
            val nexusPassword = System.getenv("NEXUS_PASSWORD")

            name = "Sonatype"
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            credentials {
                username = nexusUsername
                password = nexusPassword
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}
