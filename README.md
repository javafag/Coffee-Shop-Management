Coffee Shop Management API
A Spring Boot backend application for managing coffee shop operations, including orders, customer data, and waiter performance tracking.

Tech Stack
Java 17

Spring Boot 3.5.11

Gradle

PostgreSQL / H2 Database

Liquibase (Migrations)

SpringDoc OpenAPI (Swagger)

Key Features
REST API V1 for order management.

Waiter performance analytics using JPQL.

Automated database schema migrations via Liquibase.

Global exception handling and request validation.

Getting Started
Prerequisites
JDK 17

PostgreSQL 15 or higher

Installation
Clone the repository.

Update database credentials in src/main/resources/application.properties.

Run the application:
./gradlew bootRun

API Documentation
Once started, the Swagger UI is available at:
http://localhost:8083/swagger-ui/index.html
