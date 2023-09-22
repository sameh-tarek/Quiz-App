package com.sameh.quizapp.mappper.Impl;

import com.sameh.quizapp.dto.QuestionWrapperDto;
import com.sameh.quizapp.mappper.QuestionWrapperMapper;
import com.sameh.quizapp.model.QuestionWrapper;
import org.springframework.stereotype.Component;

@Component
public class QuestionWrapperMapperImpl implements QuestionWrapperMapper {
    @Override
    public QuestionWrapperDto mapToDto(QuestionWrapper questionWrapper) {
        QuestionWrapperDto questionWrapperDto = new QuestionWrapperDto();
        questionWrapperDto.setId(questionWrapper.getId());
        questionWrapperDto.setQuestionTitle(questionWrapper.getQuestionTitle());
        questionWrapperDto.setOption1(questionWrapper.getOption1());
        questionWrapperDto.setOption2(questionWrapper.getOption2());
        questionWrapperDto.setOption3(questionWrapper.getOption3());
        questionWrapperDto.setOption4(questionWrapper.getOption4());
        return questionWrapperDto;
    }

    @Override
    public QuestionWrapper mapToEntity(QuestionWrapperDto questionWrapperDto) {
        QuestionWrapper questionWrapper = new QuestionWrapper();
        questionWrapper.setId(questionWrapperDto.getId());
        questionWrapper.setQuestionTitle(questionWrapperDto.getQuestionTitle());
        questionWrapper.setOption1(questionWrapperDto.getOption1());
        questionWrapper.setOption2(questionWrapperDto.getOption2());
        questionWrapper.setOption3(questionWrapperDto.getOption3());
        questionWrapper.setOption4(questionWrapperDto.getOption4());

        return questionWrapper;
    }
}
