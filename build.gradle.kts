plugins {
    kotlin("jvm") version "1.9.23"
    application

    id("org.openjfx.javafxplugin") version "0.0.13"
}

application {
    mainClass.set("briscola.LauncherKt")
}

group = "briscola"
version = "1.0-SNAPSHOT"

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

javafx {
    version = "21"
    modules("javafx.controls", "javafx.fxml", "javafx.base", "javafx.graphics", "javafx.media")
}