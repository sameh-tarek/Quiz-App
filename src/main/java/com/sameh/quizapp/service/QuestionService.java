package com.sameh.quizapp.service;

import com.sameh.quizapp.dto.QuestionDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QuestionService {
    ResponseEntity<List<QuestionDto>> getAllQuestions();

    ResponseEntity<List<QuestionDto>> getQuestionsByCategory(String category);

    ResponseEntity<String> addQuestion(QuestionDto questionDto);

    ResponseEntity<String> updateQuestion(Integer id, QuestionDto questionDto);

    ResponseEntity<String> deleteQuestion(Integer id);
}
