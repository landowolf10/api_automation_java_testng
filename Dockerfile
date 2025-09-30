# Imagen base con Java 21
FROM eclipse-temurin:21-jdk-jammy

# ==========================
# Install system dependencies
# ==========================
RUN apt-get update && apt-get install -y \
    unzip \
    curl \
    wget \
    git \
    && rm -rf /var/lib/apt/lists/*

# ==========================
# Install Allure CLI
# ==========================
RUN wget https://github.com/allure-framework/allure2/releases/download/2.30.0/allure-2.30.0.zip \
    -O /tmp/allure.zip \
    && unzip /tmp/allure.zip -d /opt/ \
    && rm /tmp/allure.zip \
    && ln -s /opt/allure-2.30.0/bin/allure /usr/bin/allure

# ==========================
# Create working dir
# ==========================
WORKDIR /app

# ==========================
# Copy needed files for Gradle (for depenencies cache)
# ==========================
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

# Wrapper permission
RUN chmod +x ./gradlew

# Download dependencies (without executing tests)
RUN ./gradlew dependencies --no-daemon || true

# ==========================
# Copy source code
# ==========================
COPY src ./src

# Compile without running tests
RUN ./gradlew clean build -x test --no-daemon

# ==========================
# Default command
# ==========================
CMD ["./gradlew", "clean", "test", "allureReport"]