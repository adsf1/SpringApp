package com.example.springapp;

import com.example.springapp.course.Course;
import com.example.springapp.course.CourseDto;
import com.example.springapp.course.CourseRepository;
import com.example.springapp.error.exceptions.CourseNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CourseControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CourseRepository courseRepository;

    private static Course course;

    private static CourseDto courseDto;

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
            .body("[0].cost", equalTo(savedCourse.getCost()));
    }

    @Test
    public void testCreateCourse(){
        String requestBody = "{\n" +
                "    \"title\": \"Test Title\",\n" +
                "    \"author\": \"Test Author\",\n" +
                "    \"cost\": 9.99\n" +
                "}";
        given()
            .port(port)
            .contentType("application/json")
            .body(requestBody)
        .when()
            .post("/courses")
        .then()
            .statusCode(200)
            .body("id", equalTo(1))
            .body("title", equalTo("Test Title"))
            .body("author", equalTo("Test Author"))
            .body("cost", equalTo(9.99f));
    }

    @Test
    public void getCourseById_CourseExists_ReturnsCourse(){
        Course savedCourse = courseRepository.save(course);

        given()
        .   port(port)
        .when()
        .   get("/courses/1")
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
}
