package com.example.security.jwt.exceptionhandling;

import org.springframework.http.HttpStatus;

//This class is used for custom exception with own message and status.
public class ApiException extends RuntimeException {

    private final HttpStatus status;

    public ApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
