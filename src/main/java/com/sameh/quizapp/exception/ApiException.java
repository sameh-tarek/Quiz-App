package com.sameh.quizapp.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@AllArgsConstructor
@Data
public class ApiException {
    private final String message;
    private final int statusCode;
    private final Date timestamp;
}
