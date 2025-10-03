# 🚀 API Automation Framework with RestAssured, TestNG & Allure

![Build Status](https://img.shields.io/github/actions/workflow/status/landowolf10/api_automation_java_testng/gradle.yml?branch=main)  
![Allure Report](https://img.shields.io/badge/Allure-Report-orange)  
![Docker](https://img.shields.io/badge/Docker-ready-blue)  
![License](https://img.shields.io/badge/license-MIT-green)

---

## 📖 Overview

This repository contains a **backend/API automation testing framework** built with **Java + RestAssured + TestNG**, following industry best practices for **scalable test automation**.

✨ Key features:
- ✅ RestAssured + TestNG for API test automation
- ✅ Professional Allure Reports with detailed execution insights
- ✅ CI/CD pipelines with **GitHub Actions** & **CircleCI**
- ✅ Automatic deployment of Allure reports to **GitHub Pages** & CircleCI artifacts
- ✅ Dockerized setup for environment-independent execution

---

## ⚙️ Tech Stack

| Tool / Library  | Purpose |
|-----------------|---------|
| **Java 21**     | Programming language |
| **Gradle**      | Build & dependency management |
| **RestAssured** | API test automation |
| **TestNG**      | Test runner & suite management |
| **Allure**      | Reporting & visualization |
| **Docker**      | Containerized execution |
| **GitHub Actions + CircleCI** | CI/CD pipelines |

---

## 🧪 Running Tests Locally

| Action | Command |
|--------|---------|
| Run **all tests** | `./gradlew clean test` |
| Run **specific suite/xml file** | `./gradlew clean test "-DsuiteXmlFile=src/test/resources/suites/booking_test.xml"` |

---

## 🐳 Running Tests with Docker

| Step | Command |
|------|---------|
| Build image | `docker build -t restassured-tests .` |
| Run **all tests** | `docker run --rm restassured-tests ./gradlew clean test` |
| Run **specific suite/xml file** | `docker run --rm restassured-tests ./gradlew clean test "-DsuiteXmlFile=src/test/resources/suites/booking_test.xml"` |

---

## 📊 Allure Reports

Reports are automatically generated and published after each pipeline run.

🔗 **GitHub Pages report:** [View Allure Report](https://landowolf10.github.io/api_automation_java_testng/)

### Local Report
```bash
./gradlew allureReport
./gradlew allureServe
