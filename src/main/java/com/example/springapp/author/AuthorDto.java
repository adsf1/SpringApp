package com.example.springapp.author;

public class AuthorDto {

    private int id;

    public AuthorDto(){

    }

    public AuthorDto(Author author){
        this.id = author.getId();
    }
}
