# Utiliser l'image Maven pour construire l'application
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Utiliser l'image OpenJDK pour exécuter l'application
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080  # Note: Keep this as 8080, because it's the port your app listens to by default
ENTRYPOINT ["java", "-jar", "app.jar"]
