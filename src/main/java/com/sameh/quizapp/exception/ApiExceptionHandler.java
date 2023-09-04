package com.sameh.quizapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {ApiNotFoundException.class})
    public ResponseEntity<ApiException> handleApiNotFoundException(ApiNotFoundException e){
        //1- create payload containing exceptions details
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(
                e.getMessage(),
                notFound,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        //2- return response entity
        return new ResponseEntity<>(apiException, notFound);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ApiException> globalExceptionHandler(Exception e){
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiException apiErrorDetails = new ApiException(
                e.getMessage(),
                internalServerError,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiErrorDetails,internalServerError);
    }
}
