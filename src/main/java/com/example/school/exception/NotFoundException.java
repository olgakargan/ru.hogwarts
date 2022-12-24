package com.example.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.logging.Level;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends ApiException {
    public NotFoundException(String resourceName, String fieldName, Object fieldValue, Exception e) {
        super(String.format("Unable to find %s with %s: '%s'!", resourceName, fieldName, fieldValue),
                Level.SEVERE);










    }
}