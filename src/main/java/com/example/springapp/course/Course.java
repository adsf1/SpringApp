package com.example.springapp.course;

import jakarta.persistence.*;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private double cost;

    public Course(){

    }

    public Course(String title, String author, double cost) {
        this.title = title;
        this.author = author;
        this.cost = cost;
    }

    public int getId(){
        return this.id;
    }

    public String getTitle(){
        return this.title;
    }

    public String getAuthor(){
        return this.author;
    }

    public double getCost(){
        return this.cost;
    }
}
