package com.sameh.quizapp.dto;

import lombok.Data;

@Data
public class QuestionWrapperDto {
    private Integer id;
    private String questionTitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
}