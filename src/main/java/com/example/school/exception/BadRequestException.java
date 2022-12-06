package com.example.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.logging.Level;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends ApiException {
    public BadRequestException(String message, Throwable cause) {
        super(message, cause, Level.SEVERE);
    }

}