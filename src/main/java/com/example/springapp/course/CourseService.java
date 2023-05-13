package com.example.springapp.course;

import com.example.springapp.error.exceptions.CourseAlreadyExistsException;
import com.example.springapp.error.exceptions.CourseNotFoundException;
import com.example.springapp.error.exceptions.MissingInformationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.MissingFormatArgumentException;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    public Course createCourse(CourseDto courseDto){
        if(courseDto.getTitle() == null || courseDto.getAuthor() == null || courseDto.getCost() == null){
            throw new MissingInformationException("title", "author", "cost");
        }
        Course course = new Course(courseDto.getTitle(), courseDto.getAuthor(), courseDto.getCost());
        try{
            return courseRepository.save(course);
        } catch (Exception e) {
            throw new CourseAlreadyExistsException(courseDto.getTitle());
        }
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
