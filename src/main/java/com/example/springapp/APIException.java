package com.example.springapp;

public class APIException extends RuntimeException {

    private final int statusCode;

    private final String message;

    public APIException(int statusCode, String message){
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode(){
        return this.statusCode;
    }

    public String getMessage(){
        return this.message;
    }
}
