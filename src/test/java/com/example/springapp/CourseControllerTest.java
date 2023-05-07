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
}
