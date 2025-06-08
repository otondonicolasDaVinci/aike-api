# Usa Java 17 JDK (Temurin = OpenJDK oficial)
FROM eclipse-temurin:17-jdk

# Directorio de trabajo en el contenedor
WORKDIR /app

# Copia el JAR generado por Maven al contenedor
COPY target/aike-1.1.3.jar app.jar

# Expone el puerto por defecto de Spring Boot
EXPOSE 8080

# Comando para correr tu aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
