# Base image
FROM openjdk:17-bullseye

# Set the working directory
WORKDIR /app

# Install necessary dependencies (findutils and bash)
RUN apt-get update && apt-get install -y findutils bash

# Copy the source code and the .env file
COPY backend/. ./

RUN ./gradlew build

# Expose the port your Spring Boot application will run on
EXPOSE 8080

# Set the entry point to run the application
ENTRYPOINT ["java", "-jar", "./build/libs/idea-sync-backend-0.0.1-SNAPSHOT.jar"]