package com.sameh.quizapp.mappper.Impl;

import com.sameh.quizapp.dto.QuestionDto;
import com.sameh.quizapp.mappper.QuestionMapper;
import com.sameh.quizapp.entity.Question;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapperImpl implements QuestionMapper {
    @Override
    public QuestionDto mapToDto(Question question) {
        var questionDto = new QuestionDto();
        questionDto.setId(question.getId());
        questionDto.setQuestionTitle(question.getQuestionTitle());
        questionDto.setOption1(question.getOption1());
        questionDto.setOption2(question.getOption2());
        questionDto.setOption3(question.getOption3());
        questionDto.setOption4(question.getOption4());
        questionDto.setRightAnswer(question.getRightAnswer());
        questionDto.setDifficultLevel(question.getDifficultLevel());
        questionDto.setCategory(question.getCategory());

        return questionDto;
    }

    @Override
    public Question mapToEntity(QuestionDto questionDto) {
        var question = new Question();

        question.setId(questionDto.getId());
        question.setQuestionTitle(questionDto.getQuestionTitle());
        question.setOption1(questionDto.getOption1());
        question.setOption2(questionDto.getOption2());
        question.setOption3(questionDto.getOption3());
        question.setOption4(questionDto.getOption4());
        question.setRightAnswer(questionDto.getRightAnswer());
        question.setDifficultLevel(questionDto.getDifficultLevel());
        question.setCategory(questionDto.getCategory());

        return question;
    }
}
