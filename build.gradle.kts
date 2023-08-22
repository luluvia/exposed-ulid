val exposedVersion: String by project
val kotestVersion: String by project
val logbackVersion: String by project
val postgresVersion: String by project

plugins {
    kotlin("jvm") version "1.9.0"
    kotlin("plugin.serialization") version "1.9.0"
    `maven-publish`
    application
}

group = "net.lusade"
version = "0.1.0-SNAPSHOT"

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

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])

            groupId = group.toString()
            artifactId = "exposed-ulid"
            version = version

            pom {
                name.set("exposed-ulid")
                description.set("ULID support for Exposed")
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
}
