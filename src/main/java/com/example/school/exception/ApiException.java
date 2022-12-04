package com.example.school.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ApiException extends RuntimeException {
    public static final String UNABLE_TO_UPDATE = "Не удается обновить исключение";
    public static final String UNABLE_TO_CREATE = "Не удается создать исключение";
    public static final String FIRST_AGE_MORE_THAN_SECOND_ERROR =
            "Второе значение возраста должно быть не меньше первого!";
    public static final String ALL_FIELDS_ARE_NULL = "Поле не должно быть пустым!";
    protected final transient Logger logger = Logger.getLogger(String.valueOf(ApiException.class));

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Level level) {
        super(message);
        logger.log(level, message);
    }

}