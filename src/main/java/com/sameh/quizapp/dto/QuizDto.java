package com.sameh.quizapp.dto;

import com.sameh.quizapp.model.Question;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data

public class QuizDto {
    private Integer id;
    private String title;
    private List<Question> questions;
}