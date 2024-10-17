# Étape de construction : Utiliser l'image Maven pour construire l'application
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app

# Copier le fichier POM et le dossier src dans l'image
COPY pom.xml ./
COPY src ./src

# Construire l'application tout en sautant les tests
RUN mvn clean package -DskipTests

# Étape d'exécution : Utiliser l'image OpenJDK pour exécuter l'application
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copier le fichier JAR construit depuis l'étape précédente
COPY --from=build /app/target/*.jar app.jar

# Exposer le port sur lequel l'application écoute
EXPOSE 8080

# Point d'entrée pour exécuter l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
