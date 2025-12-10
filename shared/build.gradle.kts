plugins {
    kotlin("jvm") version "2.2.20"
}

group = "dev.dc.aoc.shared"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}
