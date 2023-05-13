package com.example.springapp.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class CourseAlreadyExistsException extends APIException {

    public CourseAlreadyExistsException(String title) {
        super(HttpStatus.BAD_REQUEST.value(), "Course '" + title + "' already exists");
    }
}
