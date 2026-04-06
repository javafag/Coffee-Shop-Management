# Stage 1: Build
FROM eclipse-temurin:17-jdk-focal AS builder

WORKDIR /app

# 1. Спершу копіюємо ВУСІ файли налаштувань Gradle
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

# 2. Тепер Gradle знає, що це за проект, і може викачати ліби
RUN ./gradlew dependencies --no-daemon

# 3. Тільки після цього копіюємо вихідний код
COPY src src

# 4. Збираємо
RUN ./gradlew build -x test --no-daemon

# Stage 2: Production (тут все без змін)
FROM eclipse-temurin:17-jre-focal

RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

RUN addgroup --system spring && adduser --system spring --ingroup spring
RUN chown -R spring:spring /app

USER spring:spring

EXPOSE 8083

ENV JAVA_OPTS="-Xms256m -Xmx512m -XX:+UseG1GC -XX:+UseStringDeduplication"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]