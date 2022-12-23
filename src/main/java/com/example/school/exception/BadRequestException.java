package com.example.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.apache.logging.log4j.Level;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends ApiException {
    public BadRequestException(String message) {
        super(message, Level.WARN);
    }

}