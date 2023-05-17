package com.example.springapp.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAllAuthors(){
        return authorRepository.findAll();
    }

    public void createAuthor(){

    }

    public void getAuthorById() {

    }

    public void updateAuthorById(){

    }

    public void deleteAuthorById() {

    }
}
