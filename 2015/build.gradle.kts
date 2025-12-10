plugins {
    kotlin("jvm") version "2.2.20"
}

group = "dev.dc.aoc.y15"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":data"))
    implementation(project(":shared"))
    implementation(libs.google.gson)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
