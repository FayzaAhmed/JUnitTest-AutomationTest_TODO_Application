# JUnitTest-AutomationTest_TODO_Application
This repository contains sample code for:
- Testing the backend side using Java unit test.
- Automation test for the frontend side using the Selenium Python library.

## Backend Testing
The backend testing code is written in Java and uses JUnit as the testing framework. <br>
The code is located in the Software-Testing-Assi3-Backend/src/test/java/com/fcai/SoftwareTesting directory.

### Requirements
To run the backend tests, you will need the following: <br>
- Java Development Kit (JDK)
- Apache Maven

### Running the Tests
To run the backend tests, navigate to the backend-testing directory and run the following command: <br>
```
mvn test
```
This will run all the tests in the project.

### Dependencies
The backend testing code has the following dependencies:
- Spring Boot 2.5.0
- Spring Boot Starter Web
- Spring Boot Starter Test
- Lombok

<br>The pom.xml file contains all the necessary dependencies. You can update the dependencies and their versions in the pom.xml file according to your project requirements.<br>

## Frontend Automation Testing
The frontend automation testing code is written in Python and uses the Selenium library. <br>
The code is located in the Software-Testing-Assi3-Frontend directory.

### Requirements
To run the frontend automation tests, you will need the following: <br>
- Python 2.6 is the minimum version needed.
- Selenium Python library
- Chrome web browser
- WebDriver for Chrome

### Running the Tests
To run the frontend automation tests, navigate to the frontend-automation-testing directory and run the following command:
```
python test.py
```
This will run the automation tests using the Chrome browser. <br>

# Conclusion
This repository provides sample code for testing the backend side using Java unit tests and automation tests for the frontend side using the Selenium Python library. Feel free to use this code as a starting point for your own testing projects. 
