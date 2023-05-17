package com.example.springapp.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<AuthorDto> getAllAuthors(){
        return authorService.getAllAuthors().stream().map(AuthorDto::new).collect(Collectors.toList());
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