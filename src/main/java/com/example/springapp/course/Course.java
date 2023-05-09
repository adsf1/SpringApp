package com.example.springapp.course;

import jakarta.persistence.*;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private float cost;

    public Course(){

    }

    public Course(String title, String author, float cost) {
        this.title = title;
        this.author = author;
        this.cost = cost;
    }

    public long getId(){
        return this.id;
    }

    public String getTitle(){
        return this.title;
    }

    public String getAuthor(){
        return this.author;
    }

    public float getCost(){
        return this.cost;
    }
}
