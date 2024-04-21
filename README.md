# Calorie counting app
The developed application provides detailed information about products, dishes and clients, helping to control nutrition. The database contains data on calories, proteins, fats and carbohydrates of foods. A personalized nutrition plan takes into account the client’s individual parameters. Users can track their calorie, protein, fat and carbohydrate intake and receive recommendations to improve their diet. This is an indispensable assistant on the path to a healthy lifestyle and achieving your goals.
***

## Project Structure

-  **[Controller](https://github.com/lerakaplich/Calories/tree/master/src/main/java/com/kaplich/calories/controller/)**<br>
-  **[Model](https://github.com/lerakaplich/Calories/tree/master/src/main/java/com/kaplich/calories/model/)**<br>
-  **[DTO](https://github.com/lerakaplich/Calories/tree/master/src/main/java/com/kaplich/calories/dto/)**<br>
-  **[Mapper](https://github.com/lerakaplich/Calories/tree/master/src/main/java/com/kaplich/calories/mapper/)**<br>
-  **[Repository](https://github.com/lerakaplich/Calories/tree/master/src/main/java/com/kaplich/calories/repository/)**<br>
-  **[Service](https://github.com/lerakaplich/Calories/tree/master/src/main/java/com/kaplich/calories/service/)**<br>
***

## Dependencies

- Java 21
- Maven
- PostgreSQL
***

## Contents
- [Task 1](#task-1)
- [Task 2](#task-2)
- [Task 3](#task-3)
- [HTTP requests](#HTTP-requests)
- [SonarCloud](#sonarCloud)
***

## Task 1
1. Create and run locally the simplest web/REST service using any open source example using Java stack: Spring (Spring Boot)/maven/gradle/Jersey/Spring MVC. 
2. Add a GET endpoint that accepts input parameters as query Params in the URL according to the option, and returns any hard-coded result in the form of JSON according to the option.
***

## Task 2
1. Connect a database to the project (PostgreSQL/MySQL/и т.д.).
- (0 - 7 points) - implementation of one-to-many communication;
- (8 - 10 points) - implementation of many-to-many communication.
2. Implement CRUD operations with all entities.
***

## Task 3
1. Add an endpoint to the GET project (it should be useful) with the parameter(s). The data must be obtained from the database using a "custom" query (@Query) with parameter(s) to the nested entity.
2. Add the simplest cache in the form of an in-memory Map (as a separate bin).
***

## [SonarCloud](https://sonarcloud.io/summary/overall?id=lerakaplich_Calories)
