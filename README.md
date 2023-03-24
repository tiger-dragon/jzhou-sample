# jzhou-sample
Sample Spring Boot project

Original Requirements:
1. A REST controller with individual services for:
Adds a User to the list of stored users using a JSON request body. The request should be rejected if the same first name/last name combination is already stored in the system.
Returns a single User when requested by ID as JSON.

2. Returns a list of all Users as JSON, ordered alphabetically by last name
3. Deletes a User when requested by ID
4. User object includes ID, first name, and, last name

5. Store user using the h2 embedded database.
6. Protect each endpoint with Spring Security.
7. Appropriate HTTP status codes
8. Mocks and Unit Test

To further demonstrate the usage of Spring Data and JPA, we have increased the scope of the project 
to include Course and Enrollment objects. 
The API provides endpoints for adding courses, persons, retrieving persons, deleting persons, enrolling
persons in a course, and retrieving all enrolled courses for a persons.

The project utilizes Spring Data JPA framework. The in-memory H2 database is used to save the data.

Java version used: Amazon Corretto 11.
