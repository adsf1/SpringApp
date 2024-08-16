package com.example.springapp.author;

import com.example.springapp.course.Course;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String name;

    private int age;

    @OneToMany(mappedBy = "author")
    private List<Course> courses;

    public Author(){

    }

    public Author(String name, int age) {
        this.name = name;
        this.age = age;
        courses = new ArrayList<>();
    }

    public int getId(){
        return this.id;
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

    public void addCourse(Course course){
        this.courses.add(course);
        course.setAuthor(this);
    }

}
