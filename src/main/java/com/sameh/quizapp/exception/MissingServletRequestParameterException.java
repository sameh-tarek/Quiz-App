package com.sameh.quizapp.exception;

public class MissingServletRequestParameterException extends RuntimeException{
    public MissingServletRequestParameterException() {
        super();
    }

    public MissingServletRequestParameterException(String message) {
        super(message);
    }
}
