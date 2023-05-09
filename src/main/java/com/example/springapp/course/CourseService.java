package com.example.springapp.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    public Course createCourse(CourseDto courseDto){
        Course course = new Course(courseDto.getTitle(), courseDto.getAuthor(), courseDto.getCost());
        return courseRepository.save(course);
    }

    public Course getCourseById(){
        return null;
    }

    public Course updateCourseById(){
        return null;
    }

    public Course deleteCourseById(){
        return null;
    }
}
