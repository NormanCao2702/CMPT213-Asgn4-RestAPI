# Build stage
FROM gradle:7.6.1-jdk17-alpine AS build
WORKDIR /app
COPY . .
RUN gradle build --no-daemon

# Run stage
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
COPY public public/

ENV PORT=8080
EXPOSE ${PORT}
CMD ["sh", "-c", "java -jar app.jar --server.port=${PORT}"]