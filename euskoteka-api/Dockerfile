# ----- Etapa 1: build -----
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn -B -q dependency:go-offline

COPY src ./src
RUN mvn -B -q package -DskipTests

# ----- Etapa 2: runtime -----
FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=prod

# IMPORTANTE: Render usa $PORT
ENV PORT=8080

EXPOSE 8080

# Hacer que Spring use el puerto dinámico asignado por Render
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT}"]
