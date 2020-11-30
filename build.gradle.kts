import org.gradle.api.file.DuplicatesStrategy.WARN
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    id("me.champeau.gradle.jmh") version "0.5.2"
}
group = "ru.sber.jmh"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

jmh {
    duplicateClassesStrategy = WARN
}

dependencies {
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.0")
    implementation("com.google.protobuf:protobuf-java:3.14.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}