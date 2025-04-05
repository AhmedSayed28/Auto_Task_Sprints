# 🛒 Luma E-Commerce Test Automation Framework

![Version](https://img.shields.io/badge/version-1.0-blue.svg)
![Java](https://img.shields.io/badge/Java-11%2B-orange)
![Selenium](https://img.shields.io/badge/Selenium-4%2B-brightgreen)
![License](https://img.shields.io/badge/license-Internal--Use--Only-lightgrey)
![Build](https://img.shields.io/badge/build-passing-brightgreen)
![Maintained](https://img.shields.io/badge/maintained-yes-success)

> **Author:** Ahmed Ghaly 
> **Company:** Sprint  
---

## 📌 Overview

This is an basic Selenium WebDriver + TestNG automation framework built to test the [Luma E-Commerce Website](https://magento.softwaretestingboard.com/). It follows the **Page Object Model (POM)** design pattern and includes:

- ✅ End-to-End Test Cases (Search, Add to Cart, Checkout)
- ✅ Scalable structure for future test scenarios
- ✅ Automated Reporting & Screenshots
- ✅ Data-Driven Testing using JSON

---

## 🛠️ Tech Stack

| Category          | Technology                | Purpose                            |
|-------------------|---------------------------|-------------------------------------|
| Core Framework    | Java + TestNG             | Test execution & assertions         |
| Browser Automation| Selenium WebDriver 4+     | Web interactions                    |
| Build Tool        | Maven                     | Dependency management               |
| Reporting         | TestNG + Custom Listener  | Execution logs & screenshots        |

---

## 📂 Project Structure

```bash
Auto_Task_Sprints/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── browserActions/       # Selenium action wrappers
│   │   │   ├── driverFactory/        # WebDriver initialization
│   │   │   ├── elementActions/       # Custom element methods
│   │   │   ├── listeners/            # TestNG listeners
│   │   │   ├── pages/                # Page Objects
│   │   │   └── utilities/            # Helpers (e.g., ScreenshotManager)
│   │   └── resources/                # Config files (if any)
│   └── test/
│       ├── java/
│       │   └── tests/                # Test classes
│       └── resources/
│           └── testData/             # Data.json
├── testng.xml                        # TestNG suite file
└── pom.xml                           # Maven configuration

* * *

🚀 Test Cases Covered
---------------------

| Test Case | Description | Key Validations |
| --- | --- | --- |
| `ProductSearchTest` | Search for "Hoodie" | Products contain "Hoodie" in name |
| `AddToCartTest` | Add product to cart | Cart quantity updates correctly |
| `CheckoutProcessTest` | Complete checkout with test data | Order confirmation message appears |

* * *

⚙️ Setup & Execution
--------------------

### 🔧 Prerequisites

*   Java 11+
    
*   Maven 3.8+
    
*   Chrome or Firefox browser
    

### 📥 1. Clone & Install

bash

`git clone [ https://github.com/AhmedSayed28/SprintsTask.git  ] cd Auto_Task_Sprints mvn clean install`

### ▶️ 2. Run Tests

**Option 1: Run all tests**

bash

`mvn test`

**Option 2: Run specific test group**

bash

`mvn test -Dgroups=smoke`

**Option 3: Run via `testng.xml` in your IDE**

* * *

📊 Reporting
------------

*   **Basic TestNG Reports (As Asked)
    
*   **Screenshots (on failure):** `target/screenshots/`
    
*   **Custom Logs:** via `TestNGListener`
    
* * *

🧠 Best Practices Followed
--------------------------

*   ✔ Page Object Model (POM)
    
*   ✔ Data-Driven Testing using JSON
    
*   ✔ Soft Assertions
    
*   ✔ Auto-Retry for flaky tests (if configured)
    

* * *

💬 Need Help?
-------------

**Contact:** qa.ahmedsayed@gmail.com or 01115081316 
