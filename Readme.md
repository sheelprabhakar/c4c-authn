# Spring Boot Authentication and Authorization Service

This project is a Spring Boot-based authentication and authorization service for microservices using JWT (JSON Web Token) and authorization using REST resources.

## Table of Contents
- Introduction
- Features
- Prerequisites
- Installation
- Usage
- Endpoints
- Contributing
- License

## Introduction
This service provides authentication and authorization functionalities for microservices. It uses JWT for secure token-based authentication and REST end points based authorization.

## Features
- User registration and login
- JWT token generation and validation
- Role-based access control

## Prerequisites
- Java 17 or higher
- Gradle 8.5 or higher

## Gradle Commands

### Build the Project
To compile the project and build the JAR file:
```bash
./gradlew build
```

### Run the Application
To run the Spring Boot application:
```bash
./gradlew bootRun
```

### Clean the Project
To remove the build directory:
```bash
./gradlew clean
```

### Run Tests
To execute the unit tests:
```bash
./gradlew test
```

### Generate a Project Report
To generate a report of the project dependencies:
```bash
./gradlew dependencies
```

### Check for Updates
To check for dependency updates:
```bash
./gradlew dependencyUpdates
```

### Run the Application with a Specific Profile
To run the application with a specific Spring profile (e.g., `dev`):
```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

### Build and Run the Application
To build the project and then run the application:
```bash
./gradlew build && ./gradlew bootRun
```

### Create a Docker Image
If you have a Dockerfile and want to build a Docker image:
```bash
./gradlew jibDockerBuild
```

Feel free to add these commands to your `README.md` file to help users understand how to perform various operations on your Spring Boot project using Gradle. If you need any more commands or further assistance, let me know!
```