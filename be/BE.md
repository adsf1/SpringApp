## Endpoints

| HTTP Request Method | Endpoint      | Goal                    |
|---------------------|---------------|-------------------------|
| GET                 | /courses      | Get all courses         |
| POST                | /courses      | Create course           |
| GET                 | /courses/{id} | Get specific course     |
| PUT                 | /courses/{id} | Update specific course  |
| DELETE              | /courses/{id} | Delete specific course  |

| HTTP Request Method | Endpoint      | Goal                    |
|---------------------|---------------|-------------------------|
| GET                 | /authors      | Get all authors         |
| POST                | /authors      | Create authors          |
| GET                 | /authors/{id} | Get specific authors    |
| PUT                 | /authors/{id} | Update specific authors |
| DELETE              | /authors/{id} | Delete specific authors |

<hr>

### Run

Run `mvn spring-boot:run` on a terminal window while on directory `SpringApp/be`.

### Test

Run `curl localhost:8080` on a terminal window while on directory `SpringApp/be` to test if the BE is running.

<hr>

#### Spring Documentation

https://docs.spring.io/spring-boot/docs/current/reference/html/index.html

#### Baeldung Spring Security

https://www.baeldung.com/security-spring
