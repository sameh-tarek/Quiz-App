package com.sameh.quizapp.service;

import com.sameh.quizapp.model.QuestionWrapper;
import com.sameh.quizapp.model.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QuizService {
    public ResponseEntity<String> createQuiz(String category, int numQ, String title);
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id);
    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses);

}
