# 1️⃣ Stage 1: Build the app using Maven
FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app

# Copy the project files
COPY pom.xml .
COPY src ./src

# Build the project
RUN mvn clean package -DskipTests

# 2️⃣ Stage 2: Run the built JAR with OpenJDK
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/target/Weekend-Support-Service-0.0.1-SNAPSHOT.jar /app.jar

# Expose the application port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/app.jar"]
