package com.example.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.logging.Level;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnableToUploadFileException extends ApiException {
    public UnableToUploadFileException(String unableToUpload, Throwable cause) {
        super(ApiException.UNABLE_TO_UPLOAD, cause, Level.SEVERE);
    }
}