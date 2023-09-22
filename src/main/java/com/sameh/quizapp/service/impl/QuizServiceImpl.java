package com.sameh.quizapp.service.impl;

import com.sameh.quizapp.Repository.QuestionRepository;
import com.sameh.quizapp.Repository.QuizRepository;
import com.sameh.quizapp.dto.QuestionWrapperDto;
import com.sameh.quizapp.dto.ResponseDto;
import com.sameh.quizapp.exception.MissingServletRequestParameterException;
import com.sameh.quizapp.exception.RecordNotFoundException;
import com.sameh.quizapp.mappper.QuestionWrapperMapper;
import com.sameh.quizapp.model.Question;
import com.sameh.quizapp.model.QuestionWrapper;
import com.sameh.quizapp.model.Quiz;
import com.sameh.quizapp.model.Response;
import com.sameh.quizapp.service.QuizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizServiceImpl implements QuizService {

    private static final Logger logger = LoggerFactory.getLogger(QuizServiceImpl.class);

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionWrapperMapper questionWrapperMapper;

    @Override
    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        logger.info("User wants to create new Quiz with category {}, number of questions is {} and the title of the Quiz is {} ", category, numQ, title);
        if (numQ <= 0) {
            logger.warn("A quiz must contain one or more questions");
            throw new RecordNotFoundException("A quiz must contain one or more questions");
        }

        List<Question> questions = questionRepository.findRandomQuestionsByCategory(category, numQ);
        if (questions.isEmpty()) {
            logger.warn("No questions found in this category: {}", category);
            throw new RecordNotFoundException("No questions found in this category: " + category);
        }

        logger.info("Found {} questions for category: {} and this is questions: {}", questions.size(), category, questions);

        if (questions.size() < numQ) {
            logger.warn("There are only {} questions in {} category and you choose {}", questions.size(), category, numQ);
            ;
            throw new RecordNotFoundException("There are only " + questions.size() + " questions in " + category + " category and you choose " + numQ);
        }

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizRepository.save(quiz);

        logger.info("Created quiz with id: {}", quiz.getId());
        logger.info("This the Quiz Questions {}", quiz.getQuestions());
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }

    @Override
    public ResponseEntity<List<QuestionWrapperDto>> getQuizQuestions(Integer id) {
        logger.info("User wants to get Quiz  with id {}", id);
        Optional<Quiz> quiz = quizRepository.findById(id);
        if (quiz.isEmpty()) {
            logger.warn("Quiz with id {} not found", id);
            throw new RecordNotFoundException("Quiz with id " + id + " not found");
        }

        logger.info("Fetching questions for quiz with id: {}", id);
        List<Question> questionsFromDb = quiz.get().getQuestions();
        List<QuestionWrapper> questionsForUsers = new ArrayList<>();
        for (Question q : questionsFromDb) {
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            questionsForUsers.add(qw);
        }

        List<QuestionWrapperDto> questionsForUsersDtos = new ArrayList<>();
        for (QuestionWrapper q : questionsForUsers) {
            QuestionWrapperDto questionWrapperDto = questionWrapperMapper.mapToDto(q);
            questionsForUsersDtos.add(questionWrapperDto);
        }


        logger.info("Quiz Questions : {}", questionsForUsersDtos);
        return ResponseEntity.ok(questionsForUsersDtos);
    }

    @Override
    public ResponseEntity<Integer> calculateResult(Integer id, List<ResponseDto> responsesDtos) {
        logger.info("User wants to submit Quiz with id {} and his responses {} to get the Results ", id, responsesDtos);
        Optional<Quiz> quiz = quizRepository.findById(id);
        if (quiz.isEmpty()) {
            logger.warn("Quiz with id {} not found", id);
            throw new RecordNotFoundException("Quiz with id " + id + " not found");
        }

        logger.info("Calculating result for quiz with id: {}", id);
        List<Question> questions = quiz.get().getQuestions();
        int right = 0;
        int i = 0;
        for (ResponseDto response : responsesDtos) {
            if (response.getResponse().equals(questions.get(i).getRightAnswer())) {
                right++;
            }
            i++;
        }

        if (responsesDtos.size() != questions.size()) {
            logger.warn("Missing Some responses");
            throw new MissingServletRequestParameterException("The number of responses must match the number of questions.");
        }

        logger.info("Result for quiz with id: {} is: {}", id, right);
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}