## TO DO

- Improve CourseControllerTest class using TDD
- DTOs need constructors?
  - Not necessarily
  - Useful if you want to create them
    without deserializing a JSON object
- Evaluate DTO architecture
  - Useful in REST APIs to prevent expose of domain objects
- Add Service Layer
- Improve Service/Controller methods
- Implement remaining endpoints

<hr>

## Endpoints

| HTTP Request Method | Endpoint      |
|---------------------|---------------|
| GET                 | /courses      |
| POST                | /courses      |
| GET                 | /courses/{id} |
| PUT                 | /courses/{id} |
| DELETE              | /courses/{id} |

<hr>

### Run

``mvn spring-boot:run``

### Test

``curl localhost:8080``

<hr>

#### Spring Documentation

https://docs.spring.io/spring-boot/docs/current/reference/html/index.html

#### Baeldung Spring Security

https://www.baeldung.com/security-spring
