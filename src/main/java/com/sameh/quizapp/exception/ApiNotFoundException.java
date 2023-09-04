package com.sameh.quizapp.exception;

public class ApiNotFoundException extends RuntimeException{

    public ApiNotFoundException() {
        super();
    }
    public ApiNotFoundException(String message) {
        super(message);
    }


//    public ApiRequestException(String message, Throwable cause) {
//        super(message, cause);
//    }

}
