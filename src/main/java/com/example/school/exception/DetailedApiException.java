package com.example.school.exception;

import org.apache.logging.log4j.Level;

public class DetailedApiException extends ApiException {
    public DetailedApiException(String exceptionDetails,
                                String resourceName,
                                String fieldName,
                                Object fieldValue,
                                Exception e,
                                Level level) {
        super(String.format("%s %s with %s: '%s'!", exceptionDetails, resourceName, fieldName, fieldValue),
                level);
    }

}