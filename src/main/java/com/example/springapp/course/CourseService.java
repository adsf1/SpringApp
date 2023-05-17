package com.example.springapp.course;

import com.example.springapp.author.Author;
import com.example.springapp.author.AuthorRepository;
import com.example.springapp.error.exceptions.AuthorNotFoundException;
import com.example.springapp.error.exceptions.CourseAlreadyExistsException;
import com.example.springapp.error.exceptions.CourseNotFoundException;
import com.example.springapp.error.exceptions.MissingInformationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    private final AuthorRepository authorRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, AuthorRepository authorRepository) {
        this.courseRepository = courseRepository;
        this.authorRepository = authorRepository;
    }

    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    public Course createCourse(CourseDto courseDto){
        if(courseDto.getTitle() == null || courseDto.getAuthorId() == null || courseDto.getCost() == null){
            throw new MissingInformationException("title", "author", "cost");
        }
        Optional<Author> optionalAuthor = authorRepository.findById(courseDto.getAuthorId());
        if(optionalAuthor.isEmpty()){
            throw new AuthorNotFoundException(courseDto.getAuthorId());
        }
        Course course = new Course(courseDto.getTitle(), optionalAuthor.get(), courseDto.getCost());
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

    public Course updateCourseById(long id, CourseDto courseDto){
        Course course = courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));

        if(courseDto.getTitle() != null){
            course.setTitle(courseDto.getTitle());
        }

        if(courseDto.getAuthorId() != null && courseDto.getAuthorId() != course.getAuthorId()){
            Optional<Author> optionalAuthor = authorRepository.findById(courseDto.getAuthorId());
            if(optionalAuthor.isEmpty()){
                throw new AuthorNotFoundException(courseDto.getAuthorId());
            }
            course.setAuthor(optionalAuthor.get());
        }

        if(courseDto.getCost() != null){
            course.setCost(courseDto.getCost());
        }

        return courseRepository.save(course);
    }

    public void deleteCourseById(long id) throws CourseNotFoundException {
        Optional<Course> course = courseRepository.findById(id);

        if(course.isPresent()){
            courseRepository.deleteById(id);
        } else {
            throw new CourseNotFoundException(id);
        }
    }
}
