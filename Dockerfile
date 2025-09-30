# ==========================
# Base image with Java 21
# ==========================
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
# Create working directory
# ==========================
WORKDIR /app

# ==========================
# Copy Gradle wrapper and config first (cache dependencies)
# ==========================
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

# Make gradlew executable
RUN chmod +x ./gradlew

# Download dependencies without running tests
RUN ./gradlew dependencies --no-daemon || true

# ==========================
# Copy source code
# ==========================
COPY src ./src

# Ensure gradlew is executable (important if copied from host)
RUN chmod +x ./gradlew

# Compile project without running tests
RUN ./gradlew clean build -x test --no-daemon

# ==========================
# Default command for CI/CD
# ==========================
CMD ["./gradlew", "clean", "test", "allureReport"]