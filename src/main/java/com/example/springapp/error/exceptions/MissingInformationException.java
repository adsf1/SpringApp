package com.example.springapp.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class MissingInformationException extends APIException {

    public MissingInformationException(String... fields) {
        super(HttpStatus.BAD_REQUEST.value(), "Fill out the necessary fields. Required fields: " + String.join(", ", fields));
    }
}
