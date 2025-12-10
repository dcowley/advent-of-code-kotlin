plugins {
    kotlin("jvm") version "2.2.20"
}

group = "dev.dc.aoc.y25"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":data"))
    implementation(project(":shared"))
    implementation(libs.google.ortools)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}
