package com.sameh.quizapp.mappper;

import com.sameh.quizapp.dto.QuizDto;
import com.sameh.quizapp.model.Quiz;


public interface QuizMapper {

    QuizDto mapToDto(Quiz quiz);

    Quiz mapToEntity(QuizDto quizDto);
}