package com.example.springapp.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CourseNotFoundException extends APIException {

    public CourseNotFoundException(long courseId) {
        super(HttpStatus.NOT_FOUND.value(), "Course " + courseId + " not found");
    }
}
