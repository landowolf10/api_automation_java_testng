# Usa la imagen oficial de Java 21
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Copia el POM primero para aprovechar la caché de Docker
COPY pom.xml .

# Instala Maven
RUN apt-get update && apt-get install -y maven

# Copia los archivos fuente
COPY src ./src

# Compila el proyecto
RUN mvn clean package -DskipTests

# Copia el script de entrada
COPY entrypoint.sh .
RUN chmod +x entrypoint.sh

# Variables de entorno para configuración
ENV ENV=dev
ENV THREADS=1
ENV TAGS=""

# Puerto para Allure Reports (si lo usas)
EXPOSE 5050

ENTRYPOINT ["./entrypoint.sh"]