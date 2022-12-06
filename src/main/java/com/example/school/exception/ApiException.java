package com.example.school.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ApiException extends RuntimeException {

    public static final String UNABLE_TO_UPLOAD = "Unable to upload file exception";

    public static final String FIRST_AGE_MORE_THAN_SECOND_ERROR =
            "The second age value must be no less than the first!";

    public static final String  ALL_FIELDS_ARE_NULL = "Еhe fields are not filled in";
    public ApiException(String message, Level level) {
        super(message);
        Logger logger = Logger.getLogger(String.valueOf(ApiException.class));
        logger.log(level, message);
    }

    public ApiException(String message, Throwable cause, Level level) {
        this(message + " cause " + cause.getMessage(), level);
    }


}