plugins {
	java
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.7"
	id("jacoco")
	kotlin("jvm")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}


val queryDslVersion = "5.1.0"

dependencies {
	// Web & Data
	implementation ("org.springframework.boot:spring-boot-starter-mail")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-mail")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.springframework.boot:spring-boot-starter-websocket")

	// Database
	implementation("org.liquibase:liquibase-core")
	runtimeOnly("org.postgresql:postgresql")
	runtimeOnly("com.h2database:h2")

	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:postgresql")
	testImplementation("org.testcontainers:wiremock:1.0.2")

	// QueryDSL (Jakarta)
	implementation("com.querydsl:querydsl-jpa:$queryDslVersion:jakarta")
	implementation("com.querydsl:querydsl-core:$queryDslVersion")
	annotationProcessor("com.querydsl:querydsl-apt:$queryDslVersion:jakarta")
	annotationProcessor("jakarta.persistence:jakarta.persistence-api")
	annotationProcessor("jakarta.annotation:jakarta.annotation-api")

	// JWT (Единая версия 0.12.6)
	implementation("io.jsonwebtoken:jjwt-api:0.12.6")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")

	// OpenAPI / Swagger
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")

	// Utils & Fixes
	implementation("org.apache.commons:commons-lang3:3.18.0")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	implementation("org.modelmapper:modelmapper:3.1.1")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("io.micrometer:micrometer-registry-prometheus:1.11.5")

	// Testing
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation(kotlin("stdlib-jdk8"))
}


configurations.all {
	resolutionStrategy.eachDependency {
		if (requested.group == "org.apache.commons" && requested.name == "commons-lang3") {
			useVersion("3.18.0")
		}
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}


jacoco {
	toolVersion = "0.8.8"
}

tasks.jacocoTestReport {
	reports {
		xml.required.set(true)
		html.required.set(true)
	}
}