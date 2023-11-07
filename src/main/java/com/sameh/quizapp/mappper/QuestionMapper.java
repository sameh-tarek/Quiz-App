package com.sameh.quizapp.mappper;

import com.sameh.quizapp.dto.QuestionDto;
import com.sameh.quizapp.entity.Question;

public interface QuestionMapper {

    QuestionDto mapToDto(Question question);

    Question mapToEntity(QuestionDto questionDto);
}