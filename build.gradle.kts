import com.github.davidmc24.gradle.plugin.avro.GenerateAvroJavaTask

plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.4.8"
	id("io.spring.dependency-management") version "1.1.7"
	id("com.github.davidmc24.gradle.plugin.avro") version "1.9.1"
}

group = "kr.co.kimga"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://packages.confluent.io/maven/") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.kafka:spring-kafka")
	implementation("org.springframework.kafka:spring-kafka-streams")
	implementation("org.apache.kafka:kafka-clients:3.7.0")
	implementation("org.apache.avro:avro:1.11.3")
	implementation("io.confluent:kafka-streams-avro-serde:7.6.1")
	implementation("io.confluent:kafka-avro-serializer:7.6.1")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("com.googlecode.aviator:aviator:5.4.3")

	implementation("io.micrometer:micrometer-registry-prometheus")

	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
}

avro {
	isCreateSetters.set(false)
}

tasks.withType<GenerateAvroJavaTask> {
	source("src/main/avro")
	include("**/*.avsc")
}

val generatedAvroDir = layout.buildDirectory.dir("generated-main-avro-java").get().asFile.absolutePath

sourceSets {
	val main by getting {
		java {
			setSrcDirs(listOf("src/main/java", generatedAvroDir))
		}
	}
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.named("compileKotlin") {
	dependsOn("generateAvroJava")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
