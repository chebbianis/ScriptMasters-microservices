# Utilisez une image compatible ARM
FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Image finale (ARM compatible)
FROM eclipse-temurin:17-jre-jammy
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8091
ENTRYPOINT ["java", "-jar", "/app.jar"]