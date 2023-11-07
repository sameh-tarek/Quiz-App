package com.sameh.quizapp.service.impl;

import com.sameh.quizapp.Repository.QuestionRepository;
import com.sameh.quizapp.Repository.QuizRepository;
import com.sameh.quizapp.dto.QuestionWrapperDto;
import com.sameh.quizapp.dto.ResponseDto;
import com.sameh.quizapp.exception.MissingServletRequestParameterException;
import com.sameh.quizapp.exception.RecordNotFoundException;
import com.sameh.quizapp.entity.Question;
import com.sameh.quizapp.entity.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuizServiceImplTest {

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuizServiceImpl quizService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createQuiz_ValidInput_CreatesQuiz() {
        // Arrange
        String category = "Test Category";
        int numQ = 2;
        String title = "Test Quiz";

        List<Question> questions = new ArrayList<>();
        questions.add(new Question());
        questions.add(new Question());

        when(questionRepository.findRandomQuestionsByCategory(category, numQ)).thenReturn(questions);
        when(quizRepository.save(new Quiz())).thenReturn(new Quiz());

        // Act
        ResponseEntity<String> response = quizService.createQuiz(category, numQ, title);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("success", response.getBody());
    }

    @Test
    void createQuiz_ZeroNumQ_ThrowsRecordNotFoundException() {
        // Arrange
        String category = "Test Category";
        int numQ = 0;
        String title = "Test Quiz";

        // Act & Assert
        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class,
                () -> quizService.createQuiz(category, numQ, title));
        assertEquals("A quiz must contain one or more questions", exception.getMessage());
    }

    @Test
    void createQuiz_NoQuestionsFound_ThrowsRecordNotFoundException() {
        // Arrange
        String category = "Test Category";
        int numQ = 2;
        String title = "Test Quiz";

        when(questionRepository.findRandomQuestionsByCategory(category, numQ)).thenReturn(new ArrayList<>());

        // Act & Assert
        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class,
                () -> quizService.createQuiz(category, numQ, title));
        assertEquals("No questions found in this category: Test Category", exception.getMessage());
    }

    @Test
    void createQuiz_NotEnoughQuestionsFound_ThrowsRecordNotFoundException() {
        // Arrange
        String category = "Test Category";
        int numQ = 3;
        String title = "Test Quiz";

        List<Question> questions = new ArrayList<>();
        questions.add(new Question());

        when(questionRepository.findRandomQuestionsByCategory(category, numQ)).thenReturn(questions);

        // Act & Assert
        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class,
                () -> quizService.createQuiz(category, numQ, title));
        assertEquals("There are only 1 questions in Test Category category and you choose 3", exception.getMessage());
    }

    @Test
    public void getQuizQuestionsTest() {
        Integer id = 1;

        List<Question> questionsFromDb = new ArrayList<>();
        questionsFromDb.add(new Question());

        Quiz quiz = new Quiz();
        quiz.setQuestions(questionsFromDb);

        when(quizRepository.findById(id)).thenReturn(Optional.of(quiz));

        ResponseEntity<List<QuestionWrapperDto>> response = quizService.getQuizQuestions(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(questionsFromDb.size(), response.getBody().size());
    }

    @Test
    public void getQuizQuestionsTestWithNoQuiz() {
        Integer id = 1;

        when(quizRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> quizService.getQuizQuestions(id));
    }
    @Test
    public void calculateResultTestWithNoQuiz() {
        Integer id = 1;

        when(quizRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> quizService.calculateResult(id, new ArrayList<>()));
    }

    @Test
    public void calculateResultTestWithNoResponses() {
        Integer id = 1;

        List<Question> questions = new ArrayList<>();
        questions.add(new Question());

        Quiz quiz = new Quiz();
        quiz.setQuestions(questions);

        when(quizRepository.findById(id)).thenReturn(Optional.of(quiz));

        assertThrows(MissingServletRequestParameterException.class, () -> quizService.calculateResult(id, new ArrayList<>()));
    }

    @Test
    public void calculateResultTestWithIncorrectResponses() {
        Integer id = 1;

        List<Question> questions = new ArrayList<>();
        Question question1 = new Question();
        question1.setRightAnswer("A");
        Question question2 = new Question();
        question2.setRightAnswer("B");
        questions.add(question1);
        questions.add(question2);

        List<ResponseDto> responses = new ArrayList<>();
        ResponseDto response1 = new ResponseDto();
        response1.setResponse("B");
        ResponseDto response2 = new ResponseDto();
        response2.setResponse("A");
        responses.add(response1);
        responses.add(response2);

        Quiz quiz = new Quiz();
        quiz.setQuestions(questions);

        when(quizRepository.findById(id)).thenReturn(Optional.of(quiz));

        ResponseEntity<Integer> result = quizService.calculateResult(id, responses);

        assertEquals(0, result.getBody());
    }

    @Test
    public void calculateResultTestWithCorrectResponses() {
        Integer id = 1;

        List<Question> questions = new ArrayList<>();
        Question question1 = new Question();
        question1.setRightAnswer("A");
        Question question2 = new Question();
        question2.setRightAnswer("B");
        questions.add(question1);
        questions.add(question2);

        List<ResponseDto> responses = new ArrayList<>();
        ResponseDto response1 = new ResponseDto();
        response1.setResponse("A");
        ResponseDto response2 = new ResponseDto();
        response2.setResponse("B");
        responses.add(response1);
        responses.add(response2);

        Quiz quiz = new Quiz();
        quiz.setQuestions(questions);

        when(quizRepository.findById(id)).thenReturn(Optional.of(quiz));

        ResponseEntity<Integer> result = quizService.calculateResult(id, responses);

        assertEquals(2, result.getBody());
    }

}