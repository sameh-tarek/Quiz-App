package com.sameh.quizapp.dto;

import lombok.Data;

@Data
public class ResponseDto {
    //question id
    private Integer id;
    //question response
    private String response;
}