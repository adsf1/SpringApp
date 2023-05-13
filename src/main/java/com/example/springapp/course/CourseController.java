package com.example.springapp.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<CourseDto> getAllCourses(){
        List<Course> courses = courseService.getAllCourses();
        return courses.stream().map(CourseDto::new).collect(Collectors.toList());
    }

    @PostMapping()
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseDto courseDto){
        Course course = courseService.createCourse(courseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CourseDto(course));
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourseById(@PathVariable long id){
        courseService.deleteCourseById(id);
    }
}