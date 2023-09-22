package com.sameh.quizapp.dto;

import lombok.Data;

@Data
public class QuestionDto {
    private Integer id;
    private String questionTitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String rightAnswer;
    private String difficultLevel;
    private String category;
}