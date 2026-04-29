package com.oluwasayo;


import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResponse notFoundError(NoSuchElementException ex) {
        return ErrorResponse.create(ex, HttpStatus.NOT_FOUND,  ex.getMessage());
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ErrorResponse unauthenticatedError(HttpClientErrorException.Unauthorized ex) {
        return ErrorResponse.create(ex, HttpStatus.UNAUTHORIZED,  ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse globalError(Exception ex) {
        ex.printStackTrace();
        return ErrorResponse.create(ex, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
}
