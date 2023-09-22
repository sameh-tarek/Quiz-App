package com.sameh.quizapp.mappper;

import com.sameh.quizapp.dto.QuestionDto;
import com.sameh.quizapp.model.Question;

public interface QuestionMapper {

    QuestionDto mapToDto(Question question);

    Question mapToEntity(QuestionDto questionDto);
}