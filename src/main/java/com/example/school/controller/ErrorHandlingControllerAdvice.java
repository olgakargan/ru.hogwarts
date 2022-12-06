package com.example.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;

import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
class ErrorHandlingControllerAdvice {
    private final Logger logger = Logger.getLogger(ErrorHandlingControllerAdvice.class.getName());

    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    @ResponseBody
    public String onMultipartException(MultipartException e) {
        logger.log(Level.SEVERE, e.getMessage());
        return e.getMessage();
    }

}