plugins {
    kotlin("jvm") version "1.9.10"
}

tasks.register<JavaExec>("run") {
    mainClass.set("MainKt")
    classpath = sourceSets["main"].runtimeClasspath
    jvmArgs = listOf("--enable-native-access=ALL-UNNAMED")
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
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.0")
}

tasks.test {
    useJUnitPlatform()
}
