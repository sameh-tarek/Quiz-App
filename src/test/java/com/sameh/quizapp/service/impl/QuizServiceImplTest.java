package com.sameh.quizapp.service.impl;

import com.sameh.quizapp.dao.QuestionDao;
import com.sameh.quizapp.dao.QuizDao;
import com.sameh.quizapp.exception.MissingServletRequestParameterException;
import com.sameh.quizapp.exception.RecordNotFoundException;
import com.sameh.quizapp.model.Question;
import com.sameh.quizapp.model.QuestionWrapper;
import com.sameh.quizapp.model.Quiz;
import com.sameh.quizapp.model.Response;
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
    private QuizDao quizDao;

    @Mock
    private QuestionDao questionDao;

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

        when(questionDao.findRandomQuestionsByCategory(category, numQ)).thenReturn(questions);
        when(quizDao.save(new Quiz())).thenReturn(new Quiz());

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

        when(questionDao.findRandomQuestionsByCategory(category, numQ)).thenReturn(new ArrayList<>());

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

        when(questionDao.findRandomQuestionsByCategory(category, numQ)).thenReturn(questions);

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

        when(quizDao.findById(id)).thenReturn(Optional.of(quiz));

        ResponseEntity<List<QuestionWrapper>> response = quizService.getQuizQuestions(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(questionsFromDb.size(), response.getBody().size());
    }

    @Test
    public void getQuizQuestionsTestWithNoQuiz() {
        Integer id = 1;

        when(quizDao.findById(id)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> quizService.getQuizQuestions(id));
    }
    @Test
    public void calculateResultTestWithNoQuiz() {
        Integer id = 1;

        when(quizDao.findById(id)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> quizService.calculateResult(id, new ArrayList<>()));
    }

    @Test
    public void calculateResultTestWithNoResponses() {
        Integer id = 1;

        List<Question> questions = new ArrayList<>();
        questions.add(new Question());

        Quiz quiz = new Quiz();
        quiz.setQuestions(questions);

        when(quizDao.findById(id)).thenReturn(Optional.of(quiz));

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

        List<Response> responses = new ArrayList<>();
        Response response1 = new Response();
        response1.setResponse("B");
        Response response2 = new Response();
        response2.setResponse("A");
        responses.add(response1);
        responses.add(response2);

        Quiz quiz = new Quiz();
        quiz.setQuestions(questions);

        when(quizDao.findById(id)).thenReturn(Optional.of(quiz));

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

        List<Response> responses = new ArrayList<>();
        Response response1 = new Response();
        response1.setResponse("A");
        Response response2 = new Response();
        response2.setResponse("B");
        responses.add(response1);
        responses.add(response2);

        Quiz quiz = new Quiz();
        quiz.setQuestions(questions);

        when(quizDao.findById(id)).thenReturn(Optional.of(quiz));

        ResponseEntity<Integer> result = quizService.calculateResult(id, responses);

        assertEquals(2, result.getBody());
    }

}