# Selenium Automation Framework

A production-ready Selenium WebDriver automation framework built with **Java**, **Maven**, **TestNG**, and **ExtentReports**, featuring a full **Jenkins CI/CD pipeline**.

## 🏗️ Framework Architecture

```
src/main/java/com/automation/
├── base/           → Base classes (driver setup, test lifecycle)
├── helper/         → Utility classes (waits, element actions, screenshots)
├── pages/          → Page Object Model classes
└── reporting/      → ExtentReports management

src/test/java/com/automation/
└── testcases/      → Test case classes

src/test/resources/
└── testdata/       → Config properties and test data files
```

## 📋 Prerequisites

- **Java JDK 11+** installed and `JAVA_HOME` set
- **Maven 3.8+** installed and on `PATH`
- **Chrome / Firefox / Edge** browser installed
- **Git** installed (for version control)

## 🚀 Quick Start

### 1. Clone the Repository
```bash
git clone <repository-url>
cd selenium-automation-framework
```

### 2. Run All Tests
```bash
mvn clean test
```

### 3. Run with Specific Browser
```bash
mvn test -Dbrowser=firefox
mvn test -Dbrowser=chrome
mvn test -Dbrowser=edge
```

### 4. Run in Headless Mode
```bash
mvn test -Dheadless=true
```

### 5. Run Specific Test Suite
```bash
mvn test -DsuiteXmlFile=testng.xml
```

## 📊 Reports

After test execution, reports are generated in the `reports/` directory:
- **ExtentReports HTML** → `reports/ExtentReport_<timestamp>.html`
- **Screenshots** → `reports/screenshots/` (captured on test failure)
- **TestNG Reports** → `target/surefire-reports/`

## 🔧 Configuration

Edit `src/test/resources/testdata/config.properties`:
```properties
browser=chrome
headless=true
base.url=https://www.google.com
implicit.wait=10
explicit.wait=15
```

## 🔄 Jenkins CI/CD Pipeline

### Jenkins Setup
1. Install required Jenkins plugins:
   - **Pipeline**
   - **HTML Publisher**
   - **TestNG Results Plugin**
2. Configure tools in Jenkins Global Configuration:
   - **JDK 11** → Name: `JDK-11`
   - **Maven** → Name: `Maven-3.9`
3. Create a new **Pipeline** job
4. Point the pipeline to this repository's `Jenkinsfile`

### Pipeline Parameters
| Parameter   | Default     | Description                          |
|-------------|-------------|--------------------------------------|
| BROWSER     | chrome      | Browser: chrome, firefox, edge       |
| HEADLESS    | true        | Run in headless mode                 |
| TEST_SUITE  | testng.xml  | TestNG suite XML file to execute     |

## 📁 Framework Components

| Component        | Description                                    |
|------------------|------------------------------------------------|
| `BaseTest`       | Test lifecycle management (setup/teardown)      |
| `DriverManager`  | WebDriver factory with headless support         |
| `WaitHelper`     | Explicit and fluent wait utilities              |
| `ElementHelper`  | Common element interaction methods              |
| `ScreenshotHelper` | Screenshot capture on failure               |
| `ExtentReportManager` | HTML report generation                   |
| `BasePage`       | Abstract page object with common methods        |

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/new-test`)
3. Commit changes (`git commit -m 'Add new test'`)
4. Push to branch (`git push origin feature/new-test`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License.
