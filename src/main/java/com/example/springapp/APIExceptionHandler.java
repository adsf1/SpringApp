package com.example.springapp;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler(APIException.class)
    public ResponseEntity<ErrorResponse> handleAPIException(APIException ex){
        ErrorResponse error = new ErrorResponse(ex.getStatusCode(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.valueOf(ex.getStatusCode()));
    }
}
