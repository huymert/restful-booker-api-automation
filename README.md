# Restful-Booker REST API Automation Testing Framework

[![Java](https://img.shields.io/badge/Java-17-orange.svg?style=for-the-badge&logo=openjdk)](https://www.oracle.com/java/)
[![Rest-Assured](https://img.shields.io/badge/Rest--Assured-5.4.0-blue.svg?style=for-the-badge)](https://rest-assured.io/)
[![TestNG](https://img.shields.io/badge/TestNG-7.10.2-red.svg?style=for-the-badge&logo=testng)](https://testng.org/)
[![Maven](https://img.shields.io/badge/Maven-3.x-C71A36.svg?style=for-the-badge&logo=apachemaven)](https://maven.apache.org/)
[![Allure Report](https://img.shields.io/badge/Allure%20Report-2.27.0-blueviolet.svg?style=for-the-badge&logo=qameta)](https://qameta.io/)
[![JSON Schema](https://img.shields.io/badge/JSON%20Schema-Draft--04-brightgreen.svg?style=for-the-badge&logo=json)](https://json-schema.org/)

A robust, enterprise-grade **REST API Automation Testing Framework** targeting the **Restful-Booker** booking platform ([restful-booker.herokuapp.com](https://restful-booker.herokuapp.com/apidoc)). Built with **Java 17**, **Rest Assured**, **TestNG**, **Jackson Databind**, strict **JSON Schema Validation**, dynamic **Request Chaining**, and rich **Allure Reporting** with automated HTTP request/response payload logging.

---

## 📌 Framework Highlights & Architecture

* **Request & Response Standardization**: Centralized `BaseApi` configuration using `RequestSpecBuilder` to encapsulate Base URI, Content-Type headers, and global filters.
* **Token Lifecycle & Auth Handling**: Dedicated authentication module to obtain, parse, and inject Bearer / Cookie auth tokens into privileged endpoints.
* **Request Chaining (`ApiDataStore`)**: Thread-safe dynamic state manager facilitating sequential API chaining:
  $$\text{POST /auth} \longrightarrow \text{POST /booking} \longrightarrow \text{GET /booking/\{id\}} \longrightarrow \text{PUT} \longrightarrow \text{PATCH} \longrightarrow \text{DELETE} \longrightarrow \text{Verify 404}$$
* **Contract & Schema Validation**: Automated JSON Schema verification (`booking-schema.json`) guaranteeing strict API contract compliance.
* **Negative & Boundary Testing**: Rigorous negative test coverage for invalid credentials, unauthorized updates, and accessing purged resources.
* **Automated HTTP Logging**: Native integration with `allure-rest-assured` to attach complete request URIs, headers, query parameters, bodies, response status codes, and JSON responses to Allure Reports.

---

## 🏗️ Project Structure

```text
restful-booker-api-automation/
├── .github/workflows/          # GitHub Actions CI pipeline
├── src/
│   ├── main/java/
│   │   ├── api/
│   │   │   └── BaseApi.java             # RequestSpecification setup & AllureRestAssured filter
│   │   └── utils/
│   │       ├── ApiDataStore.java        # Dynamic in-memory store for Token & Booking IDs
│   │       └── ConfigReader.java        # Loads baseUrl and endpoints from config.properties
│   └── test/java/
│       └── api/
│           ├── BaseApiTest.java         # Test lifecycle base class
│           ├── AuthTest.java            # Authentication positive & negative test scenarios
│           ├── BookingTest.java         # Full CRUD lifecycle & Request Chaining test suite
│           └── FilterBookingTest.java   # Query parameter filtering (names, dates)
├── src/test/resources/
│   ├── schemas/
│   │   └── booking-schema.json          # JSON Schema contract specification for Booking
│   ├── config.properties                # Base URL and API configuration parameters
│   └── testng.xml                       # TestNG test suite runner
├── pom.xml                              # Maven build config, dependencies & Allure plugins
└── README.md
```

---

## 📋 API Test Scenarios & Coverage Matrix

| ID | Method | Endpoint | Scenario | Priority | Expected Status |
| :--- | :---: | :--- | :--- | :---: | :---: |
| **`API-SC-01`** | `POST` | `/auth` | Create Auth Token with valid credentials | **High** | `200 OK` (token returned) |
| **`API-SC-02`** | `POST` | `/auth` | Attempt login with bad credentials | **Low** | `200 OK` (`reason: Bad credentials`) |
| **`API-SC-03`** | `POST` | `/booking` | Create new hotel booking entry | **High** | `200 OK` (`bookingid` generated) |
| **`API-SC-04`** | `GET` | `/booking/{id}` | Retrieve booking & validate JSON Schema | **High** | `200 OK` (schema matches) |
| **`API-SC-05`** | `PUT` | `/booking/{id}` | Full update of booking details (Auth Token) | **High** | `200 OK` (fields updated) |
| **`API-SC-06`** | `PATCH` | `/booking/{id}` | Partial update (firstname only) | **Medium** | `200 OK` (firstname updated) |
| **`API-SC-07`** | `DELETE`| `/booking/{id}` | Delete booking record (Auth Token) | **High** | `201 Created` |
| **`API-SC-08`** | `GET` | `/booking/{id}` | Verify deleted booking returns 404 | **Medium** | `404 Not Found` |
| **`API-SC-09`** | `GET` | `/booking` | Filter booking list by `firstname` & `lastname` | **Medium** | `200 OK` |
| **`API-SC-10`** | `GET` | `/booking` | Filter booking list by `checkin` & `checkout` | **Medium** | `200 OK` |

---

## 🚀 Getting Started

### Prerequisites
* **Java Development Kit (JDK)**: Version 17 or higher
* **Apache Maven**: Version 3.8+

### Clone Repository
```bash
git clone https://github.com/huymert/restful-booker-api-automation.git
cd restful-booker-api-automation
```

### Execution Commands

```bash
# Execute full API regression test suite
mvn clean test

# Execute test suite with specific testng configuration
mvn test -DsuiteXmlFile=src/test/resources/testng.xml

# Generate and launch Allure Report dashboard
mvn allure:serve
```

---

## 📊 Test Reporting (Allure Report)

Thanks to `allure-rest-assured`, every executed request automatically generates an interactive attachment inside the Allure Report:
* **HTTP Method, URL & Headers**
* **Request Body Payload (JSON format)**
* **HTTP Response Code & Status Line**
* **Response Body & Elapsed Response Time**

```bash
# Launch interactive dashboard in default web browser
mvn allure:serve

# Generate offline report package
mvn allure:report
```

---

## 👥 Project Team & Contributors

This project was developed by **Team 2** (Capstone Project) with clear ownership and responsibilities:

* **Lê Thị Hải Hà** ([@muckkho2728](https://github.com/muckkho2728)) — **API Module Lead**:
  * Designed and configured Base Rest-Assured architecture (`BaseApi.java`), `RequestSpecBuilder`, and global header specifications.
  * Implemented Auth Token lifecycle (`POST /auth`) and request chaining mechanisms.
  * Developed core test scenarios: Create Booking (`POST /booking`), Retrieve Booking Details (`GET /booking/{id}`), and JSON Schema contract validation.

* **Trần Thị Quỳnh Chi** ([@quynhchi03](https://github.com/quynhchi03)) — **API Automation Engineer**:
  * Developed mutation test scenarios: Full Update (`PUT /booking/{id}`), Partial Update (`PATCH /booking/{id}`), and Delete Booking (`DELETE /booking/{id}`).
  * Built negative testing scenarios and dynamic data-driven query filters (`FilterBookingTest.java`).
  * Configured CI/CD pipeline and automated Allure Report publishing.

* **Hà Gia Huy** ([@huymert](https://github.com/huymert)) — **Framework Architecture & Technical Support**:
  * Initialized the base automation framework skeleton (Maven configuration, TestNG suite, and Rest-Assured dependencies).
  * Provided technical advisory, optimized dynamic in-memory data persistence (`ApiDataStore`), and supported CI/CD integration.

* **Nguyễn Khánh Linh** — Team Member (Web UI Automation Module).
* **Mentor & Project Advisor**: Mr. Vũ Lê Minh.
