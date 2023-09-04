package com.sameh.quizapp.service;

import com.sameh.quizapp.model.Question;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QuestionService {
    public ResponseEntity<List<Question>> getAllQuestions();
    public ResponseEntity<List<Question>> getQuestionsByCategory(String category);
    public ResponseEntity<String> addQuestion(Question question);
    public String updateQuestion(Integer id, Question question);
    public String deleteQuestion(Integer id);
}
