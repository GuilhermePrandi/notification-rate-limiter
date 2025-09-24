plugins {
    kotlin("jvm") version "2.0.20"
    id("application")
}

application {
    mainClass.set("MainKt")
}

kotlin {
    jvmToolchain(17)
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.xerial:sqlite-jdbc:3.42.0.0")
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("ch.qos.logback:logback-classic:1.5.13")
    testImplementation("io.mockk:mockk:1.13.11")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.11.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.11.0")
}

tasks.test {
    useJUnitPlatform()
}
