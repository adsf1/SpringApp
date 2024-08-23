package com.example.springapp;

import com.example.springapp.author.Author;
import com.example.springapp.author.AuthorRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static io.restassured.RestAssured.given;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthorControllerTest { // INCOMPLETE TESTS

    @LocalServerPort
    private int port;

    @Autowired
    private AuthorRepository authorRepository;

    private static Author author;

    private final String updateRequestBody = """
                {
                    "name": "Test Author 2",
                    "age": 2
                }""";

    @BeforeAll
    public static void setUp() {
        author = new Author("Test Author", 27);
    }

    @AfterEach
    public void tearDown() {
        authorRepository.deleteAll();
        assertEquals(0, authorRepository.count(), "Database should be empty after tearDown");
    }

    @Test
    public void getAllAuthors_EmptyDatabase_EmptyResponse(){
        given()
            .port(port)
        .when()
            .get("/authors")
        .then()
                .log().all()
            .statusCode(200)
            .body(equalTo("[]"));
    }

    @Test
    public void getAllAuthors_DatabaseWithOneRecord_ReturnsOneRecord(){
        Author savedAuthor = authorRepository.save(author);
        int id = savedAuthor.getId();

        given()
            .port(port)
        .when()
            .get("/authors")
        .then()
            .statusCode(200)
            .body("size()", equalTo(1))
            .body("[0].id", equalTo(id))
            .body("[0].name", equalTo(savedAuthor.getName()))
            .body("[0].age", equalTo(savedAuthor.getAge()))
            .body("[0].courses", hasSize(0));

    }

    @Test
    public void createAuthor_ValidInput_CreatesNewAuthor(){
        String requestBody = """
                {
                    "name": "Test Author",
                    "age": 1
                }""";

        given()
            .port(port)
            .contentType("application/json")
            .body(requestBody)
        .when()
            .post("/authors")
        .then()
        .statusCode(201)
                .log().all()
            .body("size()", equalTo(4))
            .body("id", greaterThan(0))
            .body("name", equalTo("Test Author"))
            .body("age", equalTo(1))
            .body("courses", hasSize(0));
    }

    @Test
    public void createAuthor_InvalidInput_ReturnsError(){
        String requestBody = """
                {
                    "age": 1
                }""";

        given()
            .port(port)
            .contentType("application/json")
            .body(requestBody)
        .when()
            .post("/authors")
        .then()
            .statusCode(400)
            .body("message", equalTo("Fill out the necessary fields. Required fields: name, age"));

    }

    @Test
    public void getAuthorById_AuthorExists_ReturnsAuthor(){
        Author savedAuthor = authorRepository.save(author);
        int id = savedAuthor.getId();

        given()
            .port(port)
            .pathParam("id", id)
        .when()
            .get("/authors/{id}")
        .then()
            .statusCode(200)
            .body("size()", equalTo(4))
            .body("id", equalTo(id))
            .body("name", equalTo(savedAuthor.getName()))
            .body("age", equalTo(savedAuthor.getAge()))
            .body("courses", hasSize(0));
    }

    @Test
    public void getAuthorById_AuthorDoesNotExist_ReturnsError(){
        given()
            .port(port)
        .when()
            .get("/authors/0")
        .then()
            .statusCode(404)
            .body("size()", equalTo(2))
            .body("statusCode", equalTo(404))
            .body("message", equalTo("Author 0 not found"));
    }

    @Test
    public void updateAuthorById_AuthorExists_UpdatesAuthor(){
        Author savedAuthor = authorRepository.save(author);
        int id = savedAuthor.getId();

        given()
            .port(port)
            .contentType("application/json")
            .body(updateRequestBody)
            .pathParam("id", id)
        .when()
            .put("/authors/{id}")
        .then()
            .statusCode(200)
            .body("size()", equalTo(4))
            .body("id", greaterThan(0))
            .body("name", equalTo("Test Author 2"))
            .body("age", equalTo(2))
            .body("courses", hasSize(0));
    }

    @Test
    public void updateAuthorById_AuthorDoesNotExist_ReturnsError(){
        given()
            .port(port)
            .contentType("application/json")
            .body(updateRequestBody)
        .when()
            .put("/authors/0")
        .then()
            .statusCode(404)
            .body("message", equalTo("Author 0 not found"));
    }

    @Test
    public void deleteAuthorById_AuthorExists_DeletesAuthor(){
        Author savedAuthor = authorRepository.save(author);
        int id = savedAuthor.getId();

        given()
            .port(port)
            .pathParam("id", id)
        .when()
            .delete("/authors/{id}")
        .then()
            .statusCode(204);
    }

    @Test
    public void deleteAuthorById_AuthorDoesNotExist_ReturnsError(){
        given()
            .port(port)
        .when()
            .delete("/authors/0")
        .then()
        .statusCode(404)
            .body("message", equalTo("Author 0 not found"));
    }
}
