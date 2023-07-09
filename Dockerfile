# Etapa de compilación
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

# Etapa de producción
FROM openjdk:17-slim
WORKDIR /app
COPY --from=build  target/test-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "test-0.0.1-SNAPSHOT.jar"]
