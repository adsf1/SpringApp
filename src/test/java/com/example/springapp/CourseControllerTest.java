package com.example.springapp;

import com.example.springapp.course.Course;
import com.example.springapp.course.CourseDto;
import com.example.springapp.course.CourseRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CourseControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CourseRepository courseRepository;

    private static Course course;

    private static CourseDto courseDto;

    private final String requestBody = """
                {
                    "title": "Test Course",
                    "author": "Test Author",
                    "cost": 9.99
                }""";

    private final String updateRequestBody = """
                {
                    "title": "Test Course 2",
                    "author": "Test Author",
                    "cost": 10.01
                }""";

    @BeforeAll
    public static void setUp() {
        course = new Course("Test Course", "Test Author", 9.99);
        courseDto = new CourseDto(course);
    }

    @AfterEach
    public void tearDown() {
        courseRepository.deleteAll();
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

        given()
            .port(port)
        .when()
            .get("/courses")
        .then()
            .statusCode(200)
            .body("size()", equalTo(1))
            .body("[0].id", equalTo(savedCourse.getId()))
            .body("[0].title", equalTo(savedCourse.getTitle()))
            .body("[0].author", equalTo(savedCourse.getAuthor()))
            .body("[0].cost", equalTo((float) savedCourse.getCost()));
    }

    @Test
    public void createCourse_ValidInputAndCourseDoesNotExist_CreatesNewCourse(){
        given()
            .port(port)
            .contentType("application/json")
            .body(requestBody)
        .when()
            .post("/courses")
        .then()
            .statusCode(201)
            .body("size()", equalTo(4))
            .body("id", equalTo(1))
            .body("title", equalTo("Test Course"))
            .body("author", equalTo("Test Author"))
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
            .statusCode(400)
            .body("message", equalTo("Fill out the necessary fields. Required fields: title, author, cost"));
    }

    @Test
    public void createCourse_ValidInputAndCourseExists_ReturnsRepositoryError(){
        courseRepository.save(course);

        given()
            .port(port)
            .contentType("application/json")
            .body(requestBody)
        .when()
            .post("/courses")
        .then()
            .statusCode(400)
            .body("message", equalTo("Course 'Test Course' already exists"));
    }

    @Test
    public void getCourseById_CourseExists_ReturnsCourse(){
        Course savedCourse = courseRepository.save(course);

        given()
            .port(port)
        .when()
            .get("/courses/1")
        .then()
            .statusCode(200)
            .body("size()", equalTo(4))
            .body("id", equalTo((savedCourse.getId())))
            .body("title", equalTo(savedCourse.getTitle()))
            .body("author", equalTo(savedCourse.getAuthor()))
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
        courseRepository.save(course);

        given()
            .port(port)
            .contentType("application/json")
            .body(updateRequestBody)
        .when()
            .put("/courses/1")
        .then()
            .statusCode(200)
            .body("size()", equalTo(4))
            .body("id", equalTo(1))
            .body("title", equalTo("Test Course 2"))
            .body("author", equalTo("Test Author"))
            .body("cost", equalTo(10.01f));
    }

    @Test
    public void updateCourseById_CourseDoesNotExist_ReturnsError(){
        given()
            .port(port)
            .contentType("application/json")
            .body(updateRequestBody)
        .when()
            .put("/courses/0")
        .then()
            .statusCode(404)
            .body("message", equalTo("Course 0 not found"));
    }

    @Test
    public void deleteCourseById_CourseExists_DeletesCourse(){
        courseRepository.save(course);

        given()
            .port(port)
        .when()
            .delete("/courses/1")
        .then()
            .statusCode(204);
    }

    @Test
    public void deleteCourseById_CourseDoesNotExist_ReturnsError(){
        courseRepository.save(course);

        given()
            .port(port)
        .when()
            .delete("/courses/0")
        .then()
            .statusCode(404)
            .body("message", equalTo("Course 0 not found"));
    }
}
