# Swag Labs Web Automation Project

A Selenium-based UI test automation framework for the [Swag Labs](https://www.saucedemo.com/) demo e-commerce application, built with Java, TestNG, and Allure Reports.

## Live Allure Report

The latest test execution report is published automatically via GitHub Pages after every push to `main`:

**[View Allure Report](https://sohilaelabasy.github.io/swaglabs-web-automation-project/)**

---

## Project Overview

This project automates end-to-end user flows on the Swag Labs website, covering login, product browsing, cart management, and the full checkout process. It follows the **Page Object Model (POM)** design pattern for clean separation between test logic and UI interactions.

---

## Tech Stack

| Tool / Library        | Version   | Purpose                          |
|-----------------------|-----------|----------------------------------|
| Java                  | 21        | Programming language             |
| Maven                 | 3.x       | Build & dependency management    |
| Selenium WebDriver    | 4.43.0    | Browser automation               |
| TestNG                | 7.12.0    | Test framework & assertions      |
| Allure TestNG         | 2.34.0    | Test reporting                   |
| SLF4J + Logback       | 2.0.17    | Logging                          |
| GitHub Actions        | -         | CI/CD pipeline                   |
| GitHub Pages          | -         | Allure report hosting            |

---

## Project Structure

```
swaglabs-web-automation-project/
├── .github/
│   └── workflows/
│       └── tests.yml              # CI/CD pipeline definition
├── src/
│   ├── main/java/
│   │   ├── pages/                 # Page Object classes
│   │   │   ├── LoginPage.java
│   │   │   ├── LandingPage.java
│   │   │   ├── CartPage.java
│   │   │   ├── Checkout.java
│   │   │   ├── CheckoutStepTwo.java
│   │   │   └── CheckoutCompleted.java
│   │   └── utilities/             # Shared utilities
│   │       ├── DriverFactory.java
│   │       ├── SeleniumUtils.java
│   │       └── Constants.java
│   └── test/java/
│       ├── testcases/             # Test classes
│       │   ├── BaseTest.java
│       │   ├── LoginTest.java
│       │   ├── LandingPageTest.java
│       │   ├── CartPageTest.java
│       │   └── CheckoutTest.java
│       └── listeners/             # TestNG listeners
│           ├── ITest.java
│           ├── InvokedMethod.java
│           └── AllureReportListener.java
├── allure-report/                 # Generated Allure report artifacts
├── pom.xml
└── README.md
```

---

## Test Coverage

### Login Tests (`LoginTest.java`) — 10 test cases
| # | Test Case | Type |
|---|-----------|------|
| 1 | Valid login with `standard_user` | Positive |
| 2 | Login with `locked_out_user` | Negative |
| 3 | Login with invalid username | Negative |
| 4 | Login with invalid password | Negative |
| 5 | Login with empty username | Negative |
| 6 | Login with empty password | Negative |
| 7 | Login with empty username and password | Negative |
| 8 | Login with `problem_user` | Positive |
| 9 | Verify login page title | Positive |
| 10 | Login with `performance_glitch_user` | Positive |

### Landing Page Tests (`LandingPageTest.java`) — 3 test cases
- Add to cart increases cart badge count
- Remove from cart decreases cart badge count
- Cart icon navigates to cart page with the selected item

### Cart Page Tests (`CartPageTest.java`) — 4 test cases
- Cart displays the added product
- Removing a product removes it from the cart
- Removed product is no longer shown in the cart
- Checkout button navigates to the checkout page

### Checkout Tests (`CheckoutTest.java`) — 8 test cases
- Valid checkout information navigates to step two
- Error shown when first name is missing
- Error shown when last name is missing
- Error shown when postal code is missing
- Error shown when all fields are empty
- Error shown when first name and last name are missing
- Error shown when first name and postal code are missing
- Complete checkout navigates to the order completion page *(smoke)*

---

## CI/CD Pipeline

The GitHub Actions workflow (`.github/workflows/tests.yml`) runs automatically on every push or pull request to `main`/`master`.

### Pipeline Steps

1. **Checkout code** — clones the repository
2. **Set up JDK 21** — uses Temurin distribution with Maven cache
3. **Set up Chrome** — installs the latest stable Chrome browser
4. **Run TestNG suite** — executes all tests headlessly in Chrome via Maven
5. **Restore Allure history** — fetches historical trend data from the `gh-pages` branch
6. **Install Allure CLI** — installs the Allure command-line tool via npm
7. **Generate Allure report** — builds the HTML report from test results
8. **Publish to GitHub Pages** — deploys the report to the `gh-pages` branch

---

## Running Tests Locally

### Prerequisites
- Java 21+
- Maven 3.x
- Chrome / Firefox / Edge browser installed

### Run with Maven

```bash
# Run all tests on Chrome (default)
mvn clean test

# Run on a specific browser
mvn clean test -Dbrowser=chrome
mvn clean test -Dbrowser=firefox
mvn clean test -Dbrowser=edge
```

### Generate Allure Report Locally

```bash
# Install Allure CLI (requires npm)
npm install -g allure-commandline

# Generate and open the report
allure generate allure-results --clean -o allure-report
allure open allure-report
```

---

## Team

| Name | Role |
|------|------|
| **Eng. Sohila El Abbasy** | QA Automation Engineer |
| **Eng. Mahmoud Farid** | QA Automation Engineer |

---

## Repository

[https://github.com/sohilaelabasy/swaglabs-web-automation-project](https://github.com/sohilaelabasy/swaglabs-web-automation-project)
