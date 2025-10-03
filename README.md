# 🚀 API Automation Framework with RestAssured, TestNG & Allure

![Build Status](https://img.shields.io/github/actions/workflow/status/landowolf10/api_automation_java_testng/gradle.yml?branch=main)  
![Allure Report](https://img.shields.io/badge/Allure-Report-orange)  
![Docker](https://img.shields.io/badge/Docker-ready-blue)  
![License](https://img.shields.io/badge/license-MIT-green)

---

## 📖 Overview

This repository contains a **backend/API automation testing framework** built with **Java + RestAssured + TestNG**, following industry best practices for **scalable test automation**.

✨ **Key features:**
- ✅ RestAssured + TestNG for robust API test automation
- ✅ Professional **Allure Reports** with detailed execution insights
- ✅ CI/CD pipelines with **GitHub Actions** & **CircleCI**
- ✅ Automatic deployment of Allure reports to **GitHub Pages** & CircleCI artifacts
- ✅ **Dockerized setup** for environment-independent execution

---

## ⚙️ Tech Stack

| 🔧 Tool / Library   | 📌 Purpose |
|----------------------|------------|
| **Java 21**          | Programming language |
| **Gradle**           | Build & dependency management |
| **RestAssured**      | API test automation |
| **TestNG**           | Test runner & suite management |
| **Allure**           | Reporting & visualization |
| **Docker**           | Containerized execution |
| **GitHub Actions**   | CI/CD pipeline & GitHub Pages report |
| **CircleCI**         | Additional CI/CD pipelines & artifact storage |

---

## 🧪 Running Tests Locally

| Action | Command |
|--------|---------|
| Run **all tests** | `./gradlew clean test` |
| Run **specific suite (XML)** | `./gradlew clean test "-DsuiteXmlFile=src/test/resources/suites/booking_test.xml"` |

---

## 🐳 Running Tests with Docker

| Step | Command |
|------|---------|
| Build Docker image | `docker build -t restassured-tests .` |
| Run **all tests** | `docker run --rm restassured-tests ./gradlew clean test` |
| Run **specific suite (XML)** | `docker run --rm restassured-tests ./gradlew clean test "-DsuiteXmlFile=src/test/resources/suites/booking_test.xml"` |

---

## 📊 Allure Reports

Reports are automatically generated and published after each pipeline run.

🔗 **GitHub Pages report:** [View Allure Report](https://landowolf10.github.io/api_automation_java_testng/)

### ▶️ Generate Locally
```bash
./gradlew allureReport
./gradlew allureServe
```

## 🔄 CI/CD Pipelines
This project includes end-to-end CI/CD integration:

🔹 On every push, pipelines are triggered
🔹 Tests are executed inside containers
🔹 Allure Reports are automatically uploaded

✅ GitHub Actions → Publishes report to GitHub Pages
✅ CircleCI → Stores artifacts and test reports

## 📂 Project Structure
bash
Copiar código
📦 api_automation_java_testng
 ┣ 📂 src
 ┃ ┣ 📂 main/java/...         # Utilities / Config
 ┃ ┣ 📂 test/java/...         # Test classes
 ┃ ┗ 📂 resources/suites      # TestNG XML suites
 ┣ 📜 build.gradle            # Gradle build file
 ┣ 📜 Dockerfile              # Docker setup
 ┣ 📜 gradle.yml              # GitHub Actions workflow
 ┗ 📜 README.md               # Documentation
🚀 Getting Started
Clone repository:

bash
Copiar código
git clone https://github.com/landowolf10/api_automation_java_testng.git
cd api_automation_java_testng
Run tests:

```bash
Copiar código
./gradlew clean test
```

## 📌 Future Improvements
🔹 Add test data parameterization from external sources (CSV, JSON, DB)
🔹 Integration with monitoring tools (Grafana + Prometheus)
🔹 Extend tests for contract testing with tools like Pact

## 📝 License
📜 This project is licensed under the MIT License.

