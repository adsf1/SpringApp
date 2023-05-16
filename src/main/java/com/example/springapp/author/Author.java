package com.example.springapp.author;

import jakarta.persistence.*;

@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public Author(){

    }

    public int getId(){
        return this.id;
    }
}
