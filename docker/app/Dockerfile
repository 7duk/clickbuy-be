# --- Stage 1: Build with Gradle ---
FROM gradle:8.7-jdk-21-and-22 AS builder

WORKDIR /app
COPY . .

# Build project
RUN gradle build -x test --no-daemon

# --- Stage 2: Run with JDK only ---
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copy the built jar from previous stage
COPY --from=builder /app/build/libs/backend.jar .

EXPOSE 8080

# Run the app
CMD ["java", "-jar", "backend.jar"]
