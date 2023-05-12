package com.example.springapp.course;

public class CourseDto {

    private int id;

    private String title;

    private String author;

    private double cost;

    public CourseDto(){

    }

    public CourseDto(Course course){
        this.id = course.getId();
        this.title = course.getTitle();
        this.author = course.getAuthor();
        this.cost = course.getCost();
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getAuthor(){
        return this.author;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public double getCost(){
        return this.cost;
    }

    public void setCost(float cost){
        this.cost = cost;
    }
}
