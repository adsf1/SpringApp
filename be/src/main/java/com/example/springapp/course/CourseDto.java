package com.example.springapp.course;

public class CourseDto extends BaseCourseDto {

    private Long authorId;

    public CourseDto(){

    }

    public CourseDto(Course course){
        super(course);
        this.authorId = course.getAuthorId();
    }

    public Long getAuthorId(){
        return this.authorId;
    }

    public void setAuthorId(long authorId){
        this.authorId = authorId;
    }
}
