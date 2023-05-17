package com.example.springapp.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class AuthorNotFoundException extends APIException {

    public AuthorNotFoundException(long authorId) {
        super(HttpStatus.NOT_FOUND.value(), "Author " + authorId + " not found");
    }
}
