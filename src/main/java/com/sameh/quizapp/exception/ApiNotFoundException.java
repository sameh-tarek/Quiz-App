package com.sameh.quizapp.exception;

public class ApiNotFoundException extends RuntimeException {
    public ApiNotFoundException() {
    }

    public ApiNotFoundException(String message) {
        super(message);
    }
}
