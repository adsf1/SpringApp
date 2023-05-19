package com.example.springapp;

import com.example.springapp.author.Author;
import com.example.springapp.author.AuthorDto;
import com.example.springapp.author.AuthorRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthorControllerTest { // INCOMPLETE TESTS

    @LocalServerPort
    private int port;

    @Autowired
    private AuthorRepository authorRepository;

    private static Author author;

    private static AuthorDto authorDto;

    private final String requestBody = """
                {
                    "name": "Test Author",
                    "age": 1
                }""";

    private final String updateRequestBody = """
                {
                    "name": "Test Course 2",
                    "age": 2
                }""";

    @BeforeAll
    public static void setUp() {
        author = new Author("Test Author", 27);
        authorDto = new AuthorDto(author);
    }

    @AfterEach
    public void tearDown() {
        authorRepository.deleteAll();
    }

    @Test
    public void getAllAuthors_EmptyDatabase_EmptyResponse(){
        given()
            .port(port)
        .when()
            .get("/authors")
        .then()
            .statusCode(200)
            .body(equalTo("[]"));
    }

    @Test
    public void getAllAuthors_DatabaseWithOneRecord_ReturnsOneRecord(){
        Author savedAuthor = authorRepository.save(author);

        given()
            .port(port)
        .when()
            .get("/authors")
        .then()
            .statusCode(200)
            .body("size()", equalTo(1))
            .body("[0].id", equalTo(savedAuthor.getId()))
            .body("[0].name", equalTo(savedAuthor.getName()))
            .body("[0].age", equalTo(savedAuthor.getAge()))
            .body("[0].courses", hasSize(0));

    }

    @Test
    public void createAuthor_ValidInputAndCourseDoesNotExist_CreatesNewAuthor(){
        given()
            .port(port)
            .contentType("application/json")
            .body(requestBody)
        .when()
            .post("/authors")
        .then()
        .statusCode(201)
            .body("size()", equalTo(4))
            .body("id", equalTo(1))
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
    public void getAuthorById_AuthorExists_ReturnsCourse(){
        Author savedAuthor = authorRepository.save(author);

        given()
            .port(port)
        .when()
            .get("/authors/1")
        .then()
            .statusCode(200)
            .body("size()", equalTo(4))
            .body("id", equalTo((savedAuthor.getId())))
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
        authorRepository.save(author);

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
        authorRepository.save(author);

        given()
            .port(port)
        .when()
            .delete("/authors/1")
        .then()
            .statusCode(204);
    }

    @Test
    public void deleteAuthorById_AuthorDoesNotExist_ReturnsError(){
        authorRepository.save(author);

        given()
            .port(port)
        .when()
            .delete("/authors/0")
        .then()
        .statusCode(404)
            .body("message", equalTo("Author 0 not found"));
    }
}
