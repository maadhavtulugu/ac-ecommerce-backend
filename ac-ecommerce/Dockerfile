# ===========================
# Stage 1: Build the Spring Boot app
# ===========================
FROM eclipse-temurin:22-jdk-alpine AS build

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml first to leverage Docker cache
COPY mvnw .
COPY .mvn/ .mvn
COPY pom.xml .

# Download all dependencies (offline mode) to speed up builds
RUN ./mvnw dependency:go-offline -B

# Copy the source code
COPY src ./src

# Build the Spring Boot app (skip tests for faster build)
RUN ./mvnw clean package -DskipTests

# ===========================
# Stage 2: Create final lightweight image
# ===========================
FROM eclipse-temurin:22-jdk-alpine

WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/target/ac-ecommerce-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the Spring Boot app runs on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
