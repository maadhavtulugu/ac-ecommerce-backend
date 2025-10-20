# 1. Base image with JDK 22
FROM eclipse-temurin:22-jdk-alpine

# 2. Set working directory inside the container
WORKDIR /app

# 3. Copy Maven wrapper and pom.xml first for caching dependencies
COPY mvnw .
COPY .mvn/ .mvn
COPY pom.xml .

# 4. Download all dependencies (offline mode) - speeds up rebuilds
RUN ./mvnw dependency:go-offline -B

# 5. Copy the source code
COPY src ./src

# 6. Build the Spring Boot app (skip tests for faster build)
RUN ./mvnw clean package -DskipTests

# 7. Expose the port your Spring Boot app will run on
EXPOSE 8080

# 8. Run the Spring Boot JAR
CMD ["java", "-jar", "target/ac-ecommerce-0.0.1-SNAPSHOT.jar"]
