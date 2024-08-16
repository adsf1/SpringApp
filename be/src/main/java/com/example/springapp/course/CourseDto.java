package com.example.springapp.course;

public class CourseDto {

    private int id;

    private String title;

    private Long authorId;

    private Double cost;

    public CourseDto(){

    }

    public CourseDto(Course course){
        this.id = course.getId();
        this.title = course.getTitle();
        this.authorId = course.getAuthorId();
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

    public Long getAuthorId(){
        return this.authorId;
    }

    public void setAuthorId(long authorId){
        this.authorId = authorId;
    }

    public Double getCost(){
        return this.cost;
    }

    public void setCost(Double cost){
        this.cost = cost;
    }
}
