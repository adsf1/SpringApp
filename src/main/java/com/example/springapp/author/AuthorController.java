package com.example.springapp.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public void getAllAuthors(){

    }

    @PostMapping()
    public void createAuthor(){

    }

    @GetMapping("/{id}")
    public void getAuthorById(){

    }

    @PutMapping("/{id}")
    public void updateAuthorById(){

    }

    @DeleteMapping("/{id}")
    public void deleteAuthorById(){

    }
}