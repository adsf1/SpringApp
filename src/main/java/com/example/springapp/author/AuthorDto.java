package com.example.springapp.author;

import com.example.springapp.course.Course;

import java.util.List;

public class AuthorDto {

    private int id;

    private String name;

    private int age;

    private List<Course> courses;

    public AuthorDto(){

    }

    public AuthorDto(Author author){
        this.id = author.getId();
        this.name = author.getName();
        this.age = author.getAge();
        this.courses = author.getCourses();
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getAge(){
        return this.age;
    }

    public void setAge(int age){
        this.age = age;
    }

    public List<Course> getCourses(){
        return this.courses;
    }

    public void setCourses(List<Course> courses){
        this.courses = courses;
    }
}
