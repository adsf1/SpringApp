package com.example.springapp.course;

public class BaseCourseDto {

    private int id;

    private String title;

    private Double cost;

    public BaseCourseDto(){

    }

    public BaseCourseDto(Course course){
        this.id = course.getId();
        this.title = course.getTitle();
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

    public Double getCost(){
        return this.cost;
    }

    public void setCost(Double cost){
        this.cost = cost;
    }
}
