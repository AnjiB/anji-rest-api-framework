# Addids API Assignment

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
* In future, if any API needs Authentication, this feature can be easily extended
* Framework creates the log file puts in folder /logs under the root of the project which can be used for debugging.
* Framework supports parallel test execution.
* At this moment, I am relying on TestNG for reporting. This report can be found inside `/target/CustomReport/` with name `emailable-report.html.`
* Supports for Data Driven Testing

## Testing

* Written tests for all the APIs
* No API is working as expecting, there are many bugs
* For APP testing, I downloaded Android Studio in my MAC and started testing it
* Found couple of issues while I was testing in simulator.
* As we discussed in the phone conversation, I don't have any expertise in Mobile testing, but given a chance, I would like to learn and implement
* I already built a framework for UI testing using Selenium and TestNG. Please refer for reference.
* Bug Report for APIs -[Click here](https://docs.google.com/spreadsheets/d/1ebuuMK_DGYfOESgcZ4AGdOrl3fRXHp9a-SRfQAB4XOo/edit?usp=sharing)
* Bug Report for APP - [Click here](https://docs.google.com/document/d/1ZwkR9y1QyE7lgDcfP2yAe1JAQfBxEPh-Q3KWedePAC4/edit?usp=sharing)


## How to run the tests
* Go to root folder of the project
* From the command line execute command `mvn failsafe:integration-test -DautEnvironment=DEV`
* If you want to run multiple tests in parallel, please change thread count in [testng.xml](https://github.com/AnjiB/anji-adi/blob/main/anji-ada-api/testng.xml) file.



