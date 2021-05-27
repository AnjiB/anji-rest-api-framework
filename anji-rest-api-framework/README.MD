# Mendix API Assignment

Thanks you providing me an opportunity to work on this assignment. I built a RestApi Framework to test these apis.

* [Tech Stack Used in Framework](#tech-stack-used-in-framework)
* [Overview On Framework](#Overview-On-Framework)
* [Testing](#testing)
* [How to run the tests](#how-to-run-the-tests)

## Tech Stack Used in Framework

Framework is built on Java and below are the requirements to run the tests

* Java 1.8
* Maven 3.5.0 or more
* Apache HttpClient 4.4.1
* TestNG 6.14.3
* AssertJ 3.11.1

## Overview On Framework

* Api Testing Framework is built on Apache HttpClient4
* Frameworks provides flexibility to invoke any kind of API(POST, PUT, PATCH, GET, DELETE etc) inside test using RestBuilder.
* Framework creates the log file puts in folder /logs under the root of the project which can be used for debugging.
* Framework supports parallel test execution as we use TestNG as testing framework
* At this moment, I am relying on TestNG for reporting. This report can be found inside `/target/CustomReport/` with name `emailable-report.html.`
* Supports for Data Driven Testing (TestNG Supports Data Driven Testing Using dataProviders)

## Testing

* Written tests for Create Article API
* There is one major security issue with API that Tokens are not getting invalidated.
* I already built a framework for UI testing using Selenium and TestNG. Please refer for [reference](https://github.com/AnjiB/anji-selenium-projects/tree/master/AnjiSeleniumProject).

## How to run the tests
* Go to root folder of the project
* From the command line execute command `mvn failsafe:integration-test -DautEnvironment=DEV`
* If you want to run multiple tests in parallel, please change thread count in [testng.xml](https://github.com/AnjiB/anji-adi/blob/main/anji-ada-api/testng.xml) file.