# First stage: Build the application
FROM maven:3.8.7-eclipse-temurin-17 as builder

# Set the working directory inside the container
WORKDIR /app

# Copy the project files to the container
COPY . .

# Run the Maven build with the specified profile and skip tests
RUN mvn clean install -Pdocker

# Second stage: Create a lightweight image for running the app
FROM openjdk:17-jdk-slim

# Set a volume to persist temporary files if needed
VOLUME /tmp

# Copy the built JAR from the builder stage to the runtime stage
COPY --from=builder /app/target/product-parser.jar product-parser.jar

# Set the entrypoint to run the Spring Boot application
ENTRYPOINT ["java","-jar","/product-parser.jar"]