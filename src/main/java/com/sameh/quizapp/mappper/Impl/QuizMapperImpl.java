package com.sameh.quizapp.mappper.Impl;

import com.sameh.quizapp.dto.QuizDto;
import com.sameh.quizapp.mappper.QuizMapper;
import com.sameh.quizapp.entity.Quiz;
import org.springframework.stereotype.Component;

@Component
public class QuizMapperImpl implements QuizMapper {
    @Override
    public QuizDto mapToDto(Quiz quiz) {
        QuizDto quizDto = new QuizDto();
        quizDto.setId(quiz.getId());
        quizDto.setTitle(quiz.getTitle());
        quizDto.setQuestions(quiz.getQuestions());
        return quizDto;
    }

    @Override
    public Quiz mapToEntity(QuizDto quizDto) {
        Quiz quiz = new Quiz();
        quiz.setId(quizDto.getId());
        quiz.setTitle(quizDto.getTitle());
        quiz.setQuestions(quizDto.getQuestions());
        return quiz;
    }
}
