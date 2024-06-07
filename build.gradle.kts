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
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("io.github.jason-lang:interpreter:3.2.0")
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

sourceSets {
    main {
        resources {
            srcDir("src/main/asl")
        }
    }
}

file("src").listFiles()?.filter { it.extension == "mas2j" }?.forEach { mas2jFile ->
    println("Found MAS2J file: ${mas2jFile.name}")  // This will confirm whether MAS2J files are found
    task<JavaExec>("run${mas2jFile.nameWithoutExtension}") {
        group = "run"
        classpath = sourceSets.getByName("main").runtimeClasspath
        mainClass.set("jason.infra.centralised.RunCentralisedMAS")
        args(mas2jFile.path)
        standardInput = System.`in`
        javaLauncher.set(javaToolchains.launcherFor(java.toolchain))
    }
}
