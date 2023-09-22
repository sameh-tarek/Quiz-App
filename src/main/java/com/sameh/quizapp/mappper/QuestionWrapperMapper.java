package com.sameh.quizapp.mappper;

import com.sameh.quizapp.dto.QuestionWrapperDto;
import com.sameh.quizapp.model.QuestionWrapper;

public interface QuestionWrapperMapper {

    QuestionWrapperDto mapToDto(QuestionWrapper questionWrapper);


    QuestionWrapper mapToEntity(QuestionWrapperDto questionWrapperDto);
}