package com.example.springapp.author;

import com.example.springapp.error.exceptions.CourseAlreadyExistsException;
import com.example.springapp.error.exceptions.CoursesNotAllowedOnAuthorCreationException;
import com.example.springapp.error.exceptions.MissingInformationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAllAuthors(){
        return authorRepository.findAll();
    }

    public Author createAuthor(AuthorDto authorDto) throws CoursesNotAllowedOnAuthorCreationException, MissingInformationException {
        if(authorDto.getCourses() != null){
            throw new CoursesNotAllowedOnAuthorCreationException();
        }
        if(authorDto.getName() == null || authorDto.getAge() == null){
            throw new MissingInformationException("mame", "age");
        }
        Author author = new Author(authorDto.getName(), authorDto.getAge());
        return authorRepository.save(author);
    }

    public void getAuthorById() {

    }

    public void updateAuthorById(){

    }

    public void deleteAuthorById() {

    }
}
