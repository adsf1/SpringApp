package com.example.springapp.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public List<CourseDto> getAllCourses(){
        List<Course> courses = courseService.getAllCourses();
        return courses.stream().map(CourseDto::new).collect(Collectors.toList());
    }

    @PostMapping()
    public CourseDto createCourse(@RequestBody CourseDto courseDto){
        Course course = courseService.createCourse(courseDto);
        return new CourseDto(course);
    }

    @GetMapping("/{id}")
    public CourseDto getCourseById(@PathVariable long id){
        Course course = courseService.getCourseById(id);
        return new CourseDto(course);
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