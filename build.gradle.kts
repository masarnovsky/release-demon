import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot") version "2.6.5"
  id("com.diffplug.spotless") version "6.4.1"
  id("io.spring.dependency-management") version "1.0.11.RELEASE"
  kotlin("jvm") version "1.5.10"
  kotlin("plugin.spring") version "1.5.10"
  kotlin("plugin.jpa") version "1.5.10"
}

group = "by.masarnovsky"

version = "0.0.3"

java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
  mavenCentral()
  maven("https://jitpack.io")
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.6.5")
  implementation("org.springframework.boot:spring-boot-starter-web:2.6.5")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.2")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-native-mt")
  implementation("com.github.kittinunf.fuel:fuel:2.3.1")
  implementation("com.github.kittinunf.fuel:fuel-jackson:2.3.1")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("io.github.microutils:kotlin-logging:2.1.21")
  implementation("com.github.elbekD:kt-telegram-bot:1.4.1")
  implementation("com.github.kittinunf.fuel:fuel:2.3.1")
  implementation("org.liquibase:liquibase-core:4.9.0")
  runtimeOnly("mysql:mysql-connector-java:8.0.28")
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "11"
  }
}

tasks.withType<Test> { useJUnitPlatform() }

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
  kotlin { ktfmt() }
  kotlinGradle { ktfmt() }
}

springBoot { buildInfo() }

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
  this.archiveFileName.set("release-demon.jar")
}
