package com.sameh.quizapp.service;

import com.sameh.quizapp.model.QuestionWrapper;
import com.sameh.quizapp.model.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QuizService {

    ResponseEntity<String> createQuiz(String category, int numQ, String title);

    ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id);

    ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses);
}
