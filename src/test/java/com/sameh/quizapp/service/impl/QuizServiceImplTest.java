package com.sameh.quizapp.service.impl;

import com.sameh.quizapp.Repository.QuestionRepository;
import com.sameh.quizapp.Repository.QuizRepository;
import com.sameh.quizapp.dto.QuestionWrapperDto;
import com.sameh.quizapp.dto.ResponseDto;
import com.sameh.quizapp.entity.Question;
import com.sameh.quizapp.entity.Quiz;
import com.sameh.quizapp.mappper.QuestionWrapperMapper;
import com.sameh.quizapp.model.QuestionWrapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuizServiceImplTest {

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private QuestionWrapperMapper questionWrapperMapper;

    @InjectMocks
    private QuizServiceImpl quizService;


    @DisplayName("Create Quiz")
    @Test
    void ShouldCreateQuiz() {
        var question1 = new Question(1,"Which programming language is known for its 'Write Once, Run Anywhere' principle?",
                "Java", "Python", "C++", "JavaScript", "Java", "Easy", "Programming");
        List<Question> questions = new ArrayList<>(Arrays.asList(question1));
        when(questionRepository.findRandomQuestionsByCategory("Programming", 1))
                .thenReturn(questions);


        var res = quizService.createQuiz("Programming", 1, "Programming");
        assertEquals("success", res.getBody());
        assertEquals(HttpStatus.CREATED, res.getStatusCode());
    }


    @DisplayName("Get Quiz Questions")
    @Test
    void shouldGetQuizQuestions() {
        var question1 = new Question(1,"Which programming language is known for its 'Write Once, Run Anywhere' principle?",
                "Java", "Python", "C++", "JavaScript", "Java", "Easy", "Programming");

        Quiz quiz = new Quiz();
        quiz.setId(1);
        quiz.setTitle("programming");
        List<Question> quizQuestions = new ArrayList<>(Arrays.asList(question1));
        quiz.setQuestions(quizQuestions);
        when(quizRepository.findById(1)).thenReturn(Optional.of(quiz));

        var question1Wrapper = new QuestionWrapper(1,"Which programming language is known for its 'Write Once, Run Anywhere' principle?",
                "Java", "Python", "C++", "JavaScript");
        List<QuestionWrapper> questionsForUsers = new ArrayList<>(Arrays.asList(question1Wrapper));

        var question1WrapperDto = new QuestionWrapperDto(1,"Which programming language is known for its 'Write Once, Run Anywhere' principle?",
                "Java", "Python", "C++", "JavaScript");
        List<QuestionWrapperDto> questionsForUsersDtos = new ArrayList<>(Arrays.asList(question1WrapperDto));
        when(questionWrapperMapper.mapToDto(question1Wrapper)).thenReturn(question1WrapperDto);

        var result = quizService.getQuizQuestions(1);

        assertEquals(questionsForUsersDtos, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @DisplayName("Calculate Result")
    @Test
    void calculateResult() {
        var question1 = new Question(1,"Which programming language is known for its 'Write Once, Run Anywhere' principle?",
                "Java", "Python", "C++", "JavaScript", "Java", "Easy", "Programming");

        Quiz quiz = new Quiz();
        quiz.setId(1);
        quiz.setTitle("programming");
        List<Question> quizQuestions = new ArrayList<>(Arrays.asList(question1));
        quiz.setQuestions(quizQuestions);
        when(quizRepository.findById(1)).thenReturn(Optional.of(quiz));

        var responseDto1 = new ResponseDto(1, "Java");
        List<ResponseDto> responseDtos= new ArrayList<>();
        responseDtos.add(responseDto1);

        var result = quizService.calculateResult(1, responseDtos);

        assertEquals(1, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

}