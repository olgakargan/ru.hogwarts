package com.example.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestControllerAdvice
class ErrorHandlingControllerAdvice {
    private final Logger logger = Logger.getLogger(ErrorHandlingControllerAdvice.class.getName());

    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    public String onMultipartException(MultipartException e) {
        logger.log(Level.SEVERE, e.getMessage());
        return e.getMessage();
    }

}