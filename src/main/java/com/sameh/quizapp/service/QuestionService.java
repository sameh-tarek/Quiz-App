package com.sameh.quizapp.service;

import com.sameh.quizapp.model.Question;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QuestionService {
    ResponseEntity<List<Question>> getAllQuestions();

    ResponseEntity<List<Question>> getQuestionsByCategory(String category);

    ResponseEntity<String> addQuestion(Question question);

    String updateQuestion(Integer id, Question question);

    String deleteQuestion(Integer id);
}
