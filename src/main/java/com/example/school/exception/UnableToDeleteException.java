package com.example.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.logging.Level;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnableToDeleteException extends ApiException {
    public UnableToDeleteException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("Unable to delete %s with %s: '%s'!", resourceName, fieldName, fieldValue),
                Level.SEVERE);
    }
}