package com.example.springapp.course;

import com.example.springapp.error.exceptions.CourseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Course getCourseById(long id) throws CourseNotFoundException {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if(optionalCourse.isPresent()){
            return optionalCourse.get();
        } else {
            throw new CourseNotFoundException(id);
        }
    }

    public Course updateCourseById(){
        return null;
    }

    public Course deleteCourseById(){
        return null;
    }
}
