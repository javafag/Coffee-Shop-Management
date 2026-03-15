plugins {
	java
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.7"
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
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-security")

	// Database
	implementation("org.liquibase:liquibase-core")
	runtimeOnly("org.postgresql:postgresql")
	runtimeOnly("com.h2database:h2")

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

	// Testing
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
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