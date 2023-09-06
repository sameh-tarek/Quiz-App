package com.sameh.quizapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {RecordNotFoundException.class})
    public ResponseEntity<ApiException> ApiNotFoundException(RecordNotFoundException e){
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                new Date()
        );

        return new ResponseEntity<ApiException>(apiException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {DuplicateRecordException.class})
    public ResponseEntity<ApiException> DuplicateRecordException(DuplicateRecordException e){
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.CONFLICT.value(),
                new Date()
        );

        return new ResponseEntity<ApiException>(apiException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResponseEntity<ApiException> MissingServletRequestParameterException(MissingServletRequestParameterException e){
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                new Date()
        );

        return new ResponseEntity<ApiException>(apiException, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {NoUpdateFoundException.class})
    public ResponseEntity<ApiException> NoUpdateFoundException(NoUpdateFoundException e){
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                new Date()
        );

        return new ResponseEntity<ApiException>(apiException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiException> globalExceptionHandler(Exception ex) {
        ApiException message = new ApiException(
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date()
        );

        return new ResponseEntity<ApiException>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
