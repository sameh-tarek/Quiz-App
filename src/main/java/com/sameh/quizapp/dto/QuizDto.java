package com.sameh.quizapp.dto;

import com.sameh.quizapp.entity.Question;
import lombok.Data;

import java.util.List;

@Data

public class QuizDto {
    private Integer id;
    private String title;
    private List<Question> questions;
}