package com.example.springapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CourseControllerTest {

    @LocalServerPort
    private int port;

    @Test
    public void testGetAllCourses(){
        given()
            .port(port)
        .when()
            .get("/courses")
        .then()
            .statusCode(200)
            .body(equalTo("[]"));
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
}
