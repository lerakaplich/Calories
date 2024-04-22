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
- [Task 4](#task-4)
- [Task 5](#task-5)
- [Task 6](#task-6)
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

## Task 4
1. Handle 400 and 500 errors.
2. Add logging of actions and errors (aspects).
3. Connect Swagger & CheckStyle. To remove stylistic errors.
***

## Task 5
1. Add a POST method to work with a list of parameters (passed in the request body) for bulk operations, organize the service using Java 8 (Stream API, lambda expressions).
2. Unit test coverage by >80% (business logic).
***

## Task 6
1. Add a service for counting requests to the main service. The counter must be implemented as a separate class, access to which must be synchronized.
2. Using jmeter/postman or any other means, configure the load test and make sure that the request counter is working correctly under heavy load.
***

## [SonarCloud](https://sonarcloud.io/summary/overall?id=lerakaplich_Calories)
