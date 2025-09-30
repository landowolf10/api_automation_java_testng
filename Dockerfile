# Imagen base con Java 21
FROM eclipse-temurin:21-jdk-jammy

# Crear carpeta de trabajo
WORKDIR /app

# Copiar los archivos necesarios de Gradle primero (para cache de dependencias)
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

# Dar permisos al wrapper
RUN chmod +x ./gradlew

# Descargar dependencias (sin ejecutar tests)
RUN ./gradlew dependencies --no-daemon || true

# Copiar el código fuente
COPY src ./src

# Compilar sin correr tests
RUN ./gradlew clean build -x test --no-daemon

# Comando por defecto (puedes sobreescribirlo al ejecutar)
CMD ["./gradlew", "test"]
