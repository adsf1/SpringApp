package com.example.springapp.author;

import com.example.springapp.course.CourseDto;

import java.util.List;
import java.util.stream.Collectors;

public class AuthorDto {

    private int id;

    private String name;

    private Integer age;

    private List<CourseDto> courses;

    public AuthorDto(){

    }

    public AuthorDto(Author author){
        this.id = author.getId();
        this.name = author.getName();
        this.age = author.getAge();
        this.courses = author.getCourses().stream().map(CourseDto::new).collect(Collectors.toList());
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

    public Integer getAge(){
        return this.age;
    }

    public void setAge(Integer age){
        this.age = age;
    }

    public List<CourseDto> getCourses(){
        return this.courses;
    }

    public void setCourses(List<CourseDto> courses){
        this.courses = courses;
    }
}
