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


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthorControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private AuthorRepository authorRepository;

    private static Author author;

    private static AuthorDto authorDto;

    @BeforeAll
    public static void setUp() {
        author = new Author();
        authorDto = new AuthorDto(author);
    }

    @AfterEach
    public void tearDown() {
        authorRepository.deleteAll();
    }

    @Test
    public void getAllAuthors_EmptyDatabase_EmptyResponse(){

    }

    @Test
    public void getAllAuthors_DatabaseWithOneRecord_ReturnsOneRecord(){

    }

    @Test
    public void createAuthor_ValidInputAndCourseDoesNotExist_CreatesNewAuthor(){

    }

    @Test
    public void createAuthor_InvalidInput_ReturnsError(){

    }

    @Test
    public void createAuthor_ValidInputAndCourseExists_ReturnsRepositoryError(){

    }

    @Test
    public void getAuthorById_AuthorExists_ReturnsCourse(){

    }

    @Test
    public void getAuthorById_AuthorDoesNotExist_ReturnsError(){

    }

    @Test
    public void updateAuthorById_AuthorExists_UpdatesAuthor(){

    }

    @Test
    public void updateAuthorById_AuthorDoesNotExist_ReturnsError(){

    }

    @Test
    public void deleteAuthorById_AuthorExists_DeletesAuthor(){

    }

    @Test
    public void deleteAuthorById_AuthorDoesNotExist_ReturnsError(){

    }
}
