package com.sameh.quizapp.service;

import com.sameh.quizapp.dto.QuestionDto;
import com.sameh.quizapp.model.Question;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QuestionService {
    ResponseEntity<List<QuestionDto>> getAllQuestions();

    ResponseEntity<List<QuestionDto>> getQuestionsByCategory(String category);

    ResponseEntity<String> addQuestion(QuestionDto questionDto);

    String updateQuestion(Integer id, QuestionDto questionDto);

    String deleteQuestion(Integer id);
}
