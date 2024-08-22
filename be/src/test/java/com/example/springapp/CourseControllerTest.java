package com.example.springapp;

import com.example.springapp.author.Author;
import com.example.springapp.author.AuthorRepository;
import com.example.springapp.course.Course;
import com.example.springapp.course.CourseRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CourseControllerTest { // INCOMPLETE TESTS

    @LocalServerPort
    private int port;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CourseRepository courseRepository;

    private final Author author = new Author("Test Author", 27);

    private final Course course = new Course("Test Course", author, 9.99);

    private int savedAuthorId;

    private final String requestBody = """
                {
                    "title": "Test Course",
                    "authorId": %d,
                    "cost": 9.99
                }""";

    private final String updateRequestBody = """
                {
                    "title": "Test Course 2",
                    "authorId": %d,
                    "cost": 10.01
                }""";

    @BeforeEach
    public void setUp() {
        Author savedAuthor = authorRepository.save(author);
        savedAuthorId = savedAuthor.getId();
        System.out.println(savedAuthorId);
    }

    @AfterEach
    public void tearDown() {
        courseRepository.deleteAll();
        assertEquals(0, courseRepository.count(), "Database should be empty after tearDown");
        authorRepository.deleteAll();
        assertEquals(0, authorRepository.count(), "Database should be empty after tearDown");
    }

    @Test
    public void getAllCourses_EmptyDatabase_EmptyResponse(){
        given()
            .port(port)
        .when()
            .get("/courses")
        .then()
            .statusCode(200)
            .body(equalTo("[]"));
    }

    @Test
    public void getAllCourses_DatabaseWithOneRecord_ReturnsOneRecord(){
        Course savedCourse = courseRepository.save(course);
        int id = savedCourse.getId();

        given()
            .port(port)
        .when()
            .get("/courses")
        .then()
            .statusCode(200)
            .body("size()", equalTo(1))
            .body("[0].id", equalTo(id))
            .body("[0].title", equalTo(savedCourse.getTitle()))
            .body("[0].authorId", equalTo(savedAuthorId))
            .body("[0].cost", equalTo((float) savedCourse.getCost()));
    }

    @Test
    public void createCourse_ValidInputAndCourseDoesNotExist_CreatesNewCourse(){
        given()
            .port(port)
            .contentType("application/json")
            .body(requestBody.formatted(savedAuthorId))
        .when()
            .post("/courses")
        .then()
                .log().all()
            .statusCode(201)
            .body("size()", equalTo(4))
            .body("id", greaterThan(0))
            .body("title", equalTo("Test Course"))
            .body("authorId", equalTo(savedAuthorId))
            .body("cost", equalTo(9.99f));
    }

    @Test
    public void createCourse_InvalidInput_ReturnsError(){
        String requestBody = """
                {
                    "author": "Test Author",
                    "cost": 9.99
                }""";

        given()
            .port(port)
            .contentType("application/json")
            .body(requestBody)
        .when()
            .post("/courses")
        .then()
                .log().all()
            .statusCode(400)
            .body("message", equalTo("Fill out the necessary fields. Required fields: title, authorId, cost"));
    }

    @Test
    public void createCourse_ValidInputAndCourseExists_ReturnsRepositoryError(){
        courseRepository.save(course);

        given()
            .port(port)
            .contentType("application/json")
            .body(requestBody.formatted(savedAuthorId))
        .when()
            .post("/courses")
        .then()
            .statusCode(400)
            .body("message", equalTo("Course 'Test Course' already exists"));
    }

    @Test
    public void getCourseById_CourseExists_ReturnsCourse(){
        Course savedCourse = courseRepository.save(course);
        int id = savedCourse.getId();

        given()
            .port(port)
            .pathParam("id", id)
        .when()
            .get("/courses/{id}")
        .then()
            .statusCode(200)
            .body("size()", equalTo(4))
            .body("id", equalTo(id))
            .body("title", equalTo(savedCourse.getTitle()))
            .body("authorId", equalTo(savedAuthorId))
            .body("cost", equalTo(((float) savedCourse.getCost())));
    }

    @Test
    public void getCourseById_CourseDoesNotExist_ReturnsError(){
        given()
            .port(port)
        .when()
            .get("/courses/0")
        .then()
            .statusCode(404)
            .body("size()", equalTo(2))
            .body("statusCode", equalTo(404))
            .body("message", equalTo("Course 0 not found"));
    }

    @Test
    public void updateCourseById_CourseExists_UpdatesCourse(){
        Course savedCourse = courseRepository.save(course);
        int id = savedCourse.getId();

        given()
            .port(port)
            .contentType("application/json")
            .body(updateRequestBody.formatted(savedAuthorId))
            .pathParam("id", id)
        .when()
            .put("/courses/{id}")
        .then()
            .statusCode(200)
            .body("size()", equalTo(4))
            .body("id", equalTo(id))
            .body("title", equalTo("Test Course 2"))
            .body("authorId", equalTo(savedAuthorId))
            .body("cost", equalTo(10.01f));
    }

    @Test
    public void updateCourseById_CourseDoesNotExist_ReturnsError(){
        given()
            .port(port)
            .contentType("application/json")
            .body(updateRequestBody.formatted(savedAuthorId))
        .when()
            .put("/courses/0")
        .then()
            .statusCode(404)
            .body("message", equalTo("Course 0 not found"));
    }

    @Test
    public void deleteCourseById_CourseExists_DeletesCourse(){
        authorRepository.save(author);
        Course savedCourse = courseRepository.save(course);
        int id = savedCourse.getId();

        given()
            .port(port)
            .pathParam("id", id)
        .when()
            .delete("/courses/{id}")
        .then()
            .statusCode(204);
    }

    @Test
    public void deleteCourseById_CourseDoesNotExist_ReturnsError(){
        given()
            .port(port)
        .when()
            .delete("/courses/0")
        .then()
            .statusCode(404)
            .body("message", equalTo("Course 0 not found"));
    }
}
