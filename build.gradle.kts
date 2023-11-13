import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

jar.enabled = false
bootJar.enabled = false

val querydslVersion by extra ("5.0.0")

plugins {
	id("org.springframework.boot") version("3.1.5")
	id("io.spring.dependency-management") version("1.0.11.RELEASE")
	kotlin("plugin.spring") version(("1.9.20"))
	kotlin("plugin.jpa") version(("1.9.20"))
	kotlin("jvm") version(("1.9.20"))
	kotlin("kapt") version(("1.9.20"))
}

allprojects {

	group = "com.devh.cafe"

	tasks.withType<JavaCompile> {
		sourceCompatibility = "17"
		targetCompatibility = "17"
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs += "-Xjsr305=strict"
			jvmTarget = "17"
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}

	repositories {
		mavenCentral()
	}
}

subprojects {
	apply {
		plugin("kotlin")
		plugin("kotlin-spring")
		plugin("kotlin-jpa")
		plugin("kotlin-kapt")
		plugin("java")
		plugin("org.springframework.boot")
		plugin("io.spring.dependency-management")
	}

	dependencies {
		implementation("org.springframework.boot:spring-boot-starter")
		implementation("org.springframework.boot:spring-boot-starter-parent:3.1.5")

		implementation("org.springframework.boot:spring-boot-starter-data-jpa")
		implementation("org.springframework.boot:spring-boot-starter-test")

		implementation("com.querydsl:querydsl-jpa:${querydslVersion}:jakarta")
		kapt("com.querydsl:querydsl-apt:${querydslVersion}:jakarta")
		annotationProcessor("jakarta.annotation:jakarta.annotation-api")
		annotationProcessor("jakarta.persistence:jakarta.persistence-api")

		implementation("org.apache.logging.log4j:log4j-api")
		implementation("org.apache.logging.log4j:log4j-core")
		implementation("org.apache.logging.log4j:log4j-to-slf4j")

		implementation("org.apache.commons:commons-lang3")
	}
}


