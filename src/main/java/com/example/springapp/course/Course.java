package com.example.springapp.course;

import com.example.springapp.author.Author;
import jakarta.persistence.*;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false, unique = true)
    private String title;

    @ManyToOne
    private Author author;

    @Column(nullable = false)
    private double cost;

    public Course(){

    }

    public Course(String title, long authorId, double cost) {
        this.title = title;
        this.author = new Author(); // TO DO
        this.cost = cost;
    }

    public int getId(){
        return this.id;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public Author getAuthor(){
        return this.author;
    }

    public long getAuthorId(){
        return 0; // TO DO
    }

    public void setAuthor(Author author){
        this.author = author;
    }

    public void setAuthor(long authorId){
        this.author = new Author();
    }

    public double getCost(){
        return this.cost;
    }

    public void setCost(double cost){
        this.cost = cost;
    }
}
