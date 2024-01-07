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


		/* test */
		implementation("org.springframework.boot:spring-boot-starter-test")
		testImplementation("io.kotest:kotest-runner-junit5:5.+")
		testImplementation("io.kotest:kotest-assertions-core:5.+")
		testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.+")

		/* JPA */
		implementation("org.springframework.boot:spring-boot-starter-data-jpa")
		implementation("com.querydsl:querydsl-jpa:${querydslVersion}:jakarta")
		kapt("com.querydsl:querydsl-apt:${querydslVersion}:jakarta")
		annotationProcessor("jakarta.annotation:jakarta.annotation-api")
		annotationProcessor("jakarta.persistence:jakarta.persistence-api")

		/* logging */
		implementation("io.github.microutils:kotlin-logging:3.+")
	}
}


