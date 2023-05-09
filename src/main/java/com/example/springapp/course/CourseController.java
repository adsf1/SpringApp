package com.example.springapp.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public List<Course> getAllCourses(){
        return courseService.getAllCourses();
    }

    @PostMapping()
    public Course createCourse(@RequestBody CourseDto courseDto){
        return courseService.createCourse(courseDto);
    }

    @GetMapping("/{id}")
    public Course getCourseById(){
        return null;
    }

    @PutMapping("/{id}")
    public Course updateCourseById(){
        return null;
    }

    @DeleteMapping("/{id}")
    public Course deleteCourseById(){
        return null;
    }
}