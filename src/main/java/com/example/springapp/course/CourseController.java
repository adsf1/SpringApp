package com.example.springapp.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    @PostMapping()
    public Course createCourse(@RequestBody CourseDto courseDto){
        Course course = new Course(courseDto.getTitle(), courseDto.getAuthor(), courseDto.getCost());
        return courseRepository.save(course);
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