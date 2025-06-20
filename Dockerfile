# --- Etapa de Construcción (Build Stage) ---
# Usamos una imagen de Maven con Java 17 para compilar el proyecto
FROM maven:3.9.6-eclipse-temurin-17-focal AS build

# Establecemos el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos primero el pom.xml para aprovechar el cache de Docker
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiamos el resto del código fuente
COPY src ./src

# Corremos el empaquetado (saltamos los tests para acelerar el build)
RUN mvn clean package -DskipTests


# --- Etapa Final (Final Stage) ---
# Usamos una imagen de Java 17 ligera para correr la aplicación
FROM openjdk:17-jdk-slim

# Establecemos el directorio de trabajo
WORKDIR /app

# Copiamos SOLAMENTE el .jar generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto en el que corre Spring Boot (por defecto 8080)
EXPOSE 8080

# El comando que se ejecutará cuando el contenedor inicie
ENTRYPOINT ["java", "-jar", "app.jar"]