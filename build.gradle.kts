plugins {
    kotlin("jvm") version "1.9.23"
    application
}

application {
    mainClass.set("briscola.MainKt")
}

group = "briscola"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.intellij:forms_rt:7.0.3")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}