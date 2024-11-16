# Use OpenJDK 17 as base image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the Gradle files first for better caching
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

# Copy the source code
COPY src ./src
COPY public ./public

# Build the application
RUN ./gradlew build --no-daemon

# Run the application
EXPOSE 8080
ENTRYPOINT ["java","-jar","build/libs/*.jar"]