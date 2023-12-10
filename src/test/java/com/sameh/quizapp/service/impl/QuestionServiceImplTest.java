package com.sameh.quizapp.service.impl;

import com.sameh.quizapp.Repository.QuestionRepository;
import com.sameh.quizapp.dto.QuestionDto;
import com.sameh.quizapp.entity.Question;
import com.sameh.quizapp.exception.DuplicateRecordException;
import com.sameh.quizapp.exception.RecordNotFoundException;
import com.sameh.quizapp.mappper.QuestionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private QuestionMapper questionMapper;

    @InjectMocks
    private QuestionServiceImpl questionService;

    @DisplayName("Get All Questions")
    @Test
    void shouldGetAllQuestions() {
        var question1 = new Question(1,"Which programming language is known for its 'Write Once, Run Anywhere' principle?",
                "Java", "Python", "C++", "JavaScript", "Java", "Easy", "Programming");

        var question2 = new Question(2,"What is the capital of France?", "Berlin", "Paris", "Madrid", "Rome", "Paris",
                "Medium", "Geography");

        List<Question> questions = new ArrayList<>();
        questions.add(question1);
        questions.add(question2);
        when(questionRepository.findAll()).thenReturn(questions);

        var question1Dto = new QuestionDto(
                1,
                "Which programming language is known for its 'Write Once, Run Anywhere' principle?", "Java", "Python",
                "C++", "JavaScript", "Java", "Easy", "Programming");

        var question2Dto = new QuestionDto(
                2, "What is the capital of France?", "Berlin", "Paris", "Madrid", "Rome",
                "Paris", "Medium", "Geography");


        List<QuestionDto> questionDtos = new ArrayList<>();
        questionDtos.add(question1Dto);
        questionDtos.add(question2Dto);
        when(questionMapper.mapToDto(question1)).thenReturn(question1Dto);
        when(questionMapper.mapToDto(question2)).thenReturn(question2Dto);

        var result = questionService.getAllQuestions();

        assertEquals(questionDtos, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @DisplayName("Get All Questions when Empty")
    @Test
    void ShouldThrowExceptionWhenGetAllQuestionsEmpty() {
        when(questionRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(RecordNotFoundException.class, () -> {
            questionService.getAllQuestions();
        });
    }

    @DisplayName("Get Questions By Category")
    @Test
    void ShouldGetQuestionsByCategory(){
        var question1 = new Question(1,"Which programming language is known for its 'Write Once, Run Anywhere' principle?",
                "Java", "Python", "C++", "JavaScript", "Java", "Easy", "Programming");
        List<Question> questions = new ArrayList<>();
        questions.add(question1);
        when(questionRepository.findByCategory("Programming")).thenReturn(questions);

        var question1Dto = new QuestionDto(
                1,
                "Which programming language is known for its 'Write Once, Run Anywhere' principle?", "Java", "Python",
                "C++", "JavaScript", "Java", "Easy", "Programming");
        List<QuestionDto> questionDtos = new ArrayList<>();
        questionDtos.add(question1Dto);
        when(questionMapper.mapToDto(question1)).thenReturn(question1Dto);

        var result = questionService.getQuestionsByCategory("Programming");

        assertEquals(questionDtos, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());

    }

    @DisplayName("Get Questions By Category when Empty")
    @Test
    void ShouldThrowExceptionWhenGetQuestionsByCategoryEmpty() {
        when(questionRepository.findByCategory("Programming")).thenReturn(new ArrayList<>());
        assertThrows(RecordNotFoundException.class, () -> {
            questionService.getQuestionsByCategory("Programming");
        });
    }


    @DisplayName("Add Question")
    @Test
    void shouldAddQuestion() {

        var question1Dto = new QuestionDto(
                1,
                "Which programming language is known for its 'Write Once, Run Anywhere' principle?", "Java", "Python",
                "C++", "JavaScript", "Java", "Easy", "Programming");
        when(questionRepository.findByQuestionTitle("Which programming language is known for its 'Write Once, Run Anywhere' principle?"))
                .thenReturn(new ArrayList<>());


        var question1 = new Question(1,"Which programming language is known for its 'Write Once, Run Anywhere' principle?",
                "Java", "Python", "C++", "JavaScript", "Java", "Easy", "Programming");
        when(questionMapper.mapToEntity(question1Dto)).thenReturn(question1);

        List<Question> questions = new ArrayList<>();
        questions.add(question1);

        var result = questionService.addQuestion(question1Dto);

        assertEquals(1, questions.size());
        assertEquals("success", result.getBody());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @DisplayName("Add Question when question is already exist")
    @Test
    void ShouldThrowExceptionWhenAddQuestionIsAlreadyExist(){
        var question1Dto = new QuestionDto(
                1,
                "Which programming language is known for its 'Write Once, Run Anywhere' principle?", "Java", "Python",
                "C++", "JavaScript", "Java", "Easy", "Programming");

        var question1 = new Question(1,"Which programming language is known for its 'Write Once, Run Anywhere' principle?",
                "Java", "Python", "C++", "JavaScript", "Java", "Easy", "Programming");

        when(questionRepository.findByQuestionTitle("Which programming language is known for its 'Write Once, Run Anywhere' principle?"))
                .thenReturn(new ArrayList<>(Arrays.asList(question1)));

        assertThrows(DuplicateRecordException.class, () -> {
           questionService.addQuestion(question1Dto);
        });
    }

    @DisplayName("Update Question")
    @Test
    void shouldUpdateQuestion() {
        var question1 = new Question(1,"Which programming language is known for its 'Write Once, Run Anywhere' principle?",
                "Java", "Python", "C++", "JavaScript", "Java", "Easy", "Programming");
        when(questionRepository.findById(1)).thenReturn(Optional.of(question1));

        var question1Dto = new QuestionDto(
                1,
                "Which bla bla bla?", "Java", "Python",
                "C++", "JavaScript", "Java", "Easy", "Programming");

        var question1Update = new Question(1,"Which bla bla bla?",
                "Java", "Python", "C++", "JavaScript", "Java", "Easy", "Programming");
        when(questionRepository.findById(1)).thenReturn(Optional.of(question1));
        when(questionMapper.mapToEntity(question1Dto)).thenReturn(question1Update);

        var result = questionService.updateQuestion(1, question1Dto);
        assertEquals("success", result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @DisplayName("Update Question There Is No Exist")
    @Test
    void shouldThrowRecordNotFoundExceptionWhenUpdateQuestionThereIsNoExist() {
        when(questionRepository.findById(1)).thenReturn(Optional.empty());
        var question1Dto = new QuestionDto(
                1,
                "Which bla bla bla?", "Java", "Python",
                "C++", "JavaScript", "Java", "Easy", "Programming");

        assertThrows(RecordNotFoundException.class, () -> {
            questionService.updateQuestion(1, question1Dto);
        });
    }

    @DisplayName("Delete Existing Question")
    @Test
    void shouldDeleteExistingQuestion() {
        var questionId = 1;
        var existingQuestion = new Question(
                questionId,
                "Question text",
                "Option A", "Option B", "Option C", "Option D",
                "Correct answer", "Difficulty", "Category");

        when(questionRepository.findById(questionId)).thenReturn(Optional.of(existingQuestion));

        ResponseEntity<String> response = questionService.deleteQuestion(questionId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @DisplayName("Delete Non-Existent Question")
    @Test
    void shouldThrowRecordNotFoundExceptionWhenDeleteNonExistentQuestion() {
        var nonExistentQuestionId = 999;

        when(questionRepository.findById(nonExistentQuestionId)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () ->
                questionService.deleteQuestion(nonExistentQuestionId));
    }
}