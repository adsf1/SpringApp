## TO DO

- Improve CourseControllerTest class using TDD
- Improve Service/Controller methods
- Implement remaining endpoints

#### Tests

- updateCourseById_CourseExists_UpdatesCourse
- updateCourseById_CourseDoesNotExist_ThrowsException

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
