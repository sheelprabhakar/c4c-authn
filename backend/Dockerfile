# Use the official OpenJDK 21 image as the base image
FROM openjdk:21-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot application JAR file into the container
COPY build/libs/your-application.jar /app/your-application.jar

# Expose the port that the Spring Boot application will run on
EXPOSE 8080

# Set the entry point to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/your-application.jar"]