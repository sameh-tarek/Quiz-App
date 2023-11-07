package com.sameh.quizapp.service;

import com.sameh.quizapp.dto.QuestionWrapperDto;
import com.sameh.quizapp.dto.ResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QuizService {

    ResponseEntity<String> createQuiz(String category, int numQ, String title);

    ResponseEntity<List<QuestionWrapperDto>> getQuizQuestions(Integer id);

    ResponseEntity<Integer> calculateResult(Integer id, List<ResponseDto> responsesDtos);
}
