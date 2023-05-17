package com.example.springapp.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class CoursesNotAllowedOnAuthorCreationException extends APIException {

    public CoursesNotAllowedOnAuthorCreationException() {
        super(HttpStatus.BAD_REQUEST.value(), "Courses not allowed on author creation");
    }
}
