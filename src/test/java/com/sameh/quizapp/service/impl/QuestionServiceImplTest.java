package com.sameh.quizapp.service.impl;

import com.sameh.quizapp.dao.QuestionDao;
import com.sameh.quizapp.exception.DuplicateRecordException;
import com.sameh.quizapp.exception.MissingServletRequestParameterException;
import com.sameh.quizapp.exception.NoUpdateFoundException;
import com.sameh.quizapp.exception.RecordNotFoundException;
import com.sameh.quizapp.model.Question;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {

    @Mock
    private QuestionDao questionDao;

    @InjectMocks
    private QuestionServiceImpl underTest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllQuestions() {
        // Arrange
        List<Question> questions = new ArrayList<>();
        questions.add(new Question());
        when(questionDao.findAll()).thenReturn(questions);

        // Act
        ResponseEntity<List<Question>> responseEntity = underTest.getAllQuestions();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(questions, responseEntity.getBody());
        verify(questionDao, times(1)).findAll();
    }

    @Test
    void testGetAllQuestionsNoQuestionsFound() {
        // Arrange
        when(questionDao.findAll()).thenReturn(new ArrayList<>());

        // Act and Assert
        assertThrows(RecordNotFoundException.class, () -> underTest.getAllQuestions());
        verify(questionDao, times(1)).findAll();
    }

    @Test
    void testGetQuestionsByCategory() {
        // Arrange
        String category = "example";
        List<Question> questions = new ArrayList<>();
        questions.add(new Question());
        when(questionDao.findByCategory(category)).thenReturn(questions);

        // Act
        ResponseEntity<List<Question>> responseEntity = underTest.getQuestionsByCategory(category);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(questions, responseEntity.getBody());
        verify(questionDao, times(1)).findByCategory(category);
    }

    @Test
    void testGetQuestionsByCategoryNoQuestionsFound() {
        // Arrange
        String category = "example";
        when(questionDao.findByCategory(category)).thenReturn(new ArrayList<>());

        // Act and Assert
        assertThrows(RecordNotFoundException.class, () -> underTest.getQuestionsByCategory(category));
        verify(questionDao, times(1)).findByCategory(category);
    }


    @Test
    public void testAddQuestion_Success() {
        // Arrange
        Question question = new Question();
        question.setQuestionTitle("New Question");

        when(questionDao.findByQuestionTitle(question.getQuestionTitle())).thenReturn(new ArrayList<>());

        // Act
        ResponseEntity<String> response = underTest.addQuestion(question);

        // Assert
        verify(questionDao, times(1)).findByQuestionTitle(question.getQuestionTitle());
        verify(questionDao, times(1)).save(question);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals("success", response.getBody());
    }

    @Test
    public void testAddQuestion_DuplicateRecordException() {
        // Arrange
        Question question = new Question();
        question.setQuestionTitle("Existing Question");

        List<Question> existingQuestions = new ArrayList<>();
        existingQuestions.add(question);
        when(questionDao.findByQuestionTitle(question.getQuestionTitle())).thenReturn(existingQuestions);

        // Act & Assert
        assertThrows(DuplicateRecordException.class, () -> {
            underTest.addQuestion(question);
        });
    }

    @Test
    public void testUpdateQuestion_Success() {
        // Arrange
        Integer id = 1;
        Question existingQuestion = new Question();
        existingQuestion.setQuestionTitle("Existing Title");
        when(questionDao.findById(id)).thenReturn(Optional.of(existingQuestion));

        Question updatedQuestion = new Question();
        updatedQuestion.setQuestionTitle("Updated Title");

        // Act
        String result = underTest.updateQuestion(id, updatedQuestion);

        // Assert

        Assertions.assertEquals("success", result);
    }

    @Test
    public void testUpdateQuestion_RecordNotFoundException() {
        // Arrange
        Integer id = 1;
        when(questionDao.findById(id)).thenReturn(Optional.empty());

        Question updatedQuestion = new Question();
        updatedQuestion.setQuestionTitle("Updated Title");

        // Act & Assert
        RecordNotFoundException exception = Assertions.assertThrows(RecordNotFoundException.class, () -> {
            underTest.updateQuestion(id, updatedQuestion);
        });

        Assertions.assertEquals("No question found with ID: " + id, exception.getMessage());
    }

    @Test
    public void testUpdateQuestion_MissingServletRequestParameterException() {
        // Arrange
        Integer id = 1;
        Question existingQuestion = new Question();
        existingQuestion.setQuestionTitle("Existing Title");
        when(questionDao.findById(id)).thenReturn(Optional.of(existingQuestion));

        Question updatedQuestion = new Question();

        // Act & Assert
        MissingServletRequestParameterException exception = Assertions.assertThrows(MissingServletRequestParameterException.class, () -> {
            underTest.updateQuestion(id, updatedQuestion);
        });

        Assertions.assertEquals("Question title cannot be empty", exception.getMessage());
    }

    @Test
    public void testUpdateQuestion_NoUpdateFoundException() {
        // Arrange
        Integer id = 1;
        Question existingQuestion = new Question();
        existingQuestion.setQuestionTitle("Existing Title");
        when(questionDao.findById(id)).thenReturn(Optional.of(existingQuestion));

        Question updatedQuestion = existingQuestion;

        // Act & Assert
        NoUpdateFoundException exception = Assertions.assertThrows(NoUpdateFoundException.class, () -> {
            underTest.updateQuestion(id, updatedQuestion);
        });

        Assertions.assertEquals("not found any update", exception.getMessage());
    }

    @Test
    void testDeleteQuestion() {
        // Arrange
        Integer id = 1;
        Question question = new Question();
        when(questionDao.findById(id)).thenReturn(Optional.of(question));

        // Act
        String result = underTest.deleteQuestion(id);

        // Assert
        assertEquals("success", result);
        verify(questionDao, times(1)).findById(id);
        verify(questionDao, times(1)).deleteById(id);
    }

    @Test
    void testDeleteQuestionRecordNotFound() {
        // Arrange
        Integer id = 1;
        when(questionDao.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(RecordNotFoundException.class, () -> underTest.deleteQuestion(id));
        verify(questionDao, times(1)).findById(id);
        verify(questionDao, never()).deleteById(id);
    }
}