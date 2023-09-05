package com.sameh.quizapp.exception;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class ApiException {
    private final String message;
    private final int statusCode;
    private final Date timestamp;

    public ApiException(String message, int statusCode, Date timestamp) {
        this.message = message;
        this.statusCode = statusCode;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
