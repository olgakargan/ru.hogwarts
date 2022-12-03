package com.example.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.logging.Level;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnableToUpdateException extends ApiException {
    public UnableToUpdateException(String message, Throwable cause) {
        super(message + " cause " + cause.getMessage(), Level.SEVERE);
    }
}