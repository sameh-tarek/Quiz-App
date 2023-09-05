package com.sameh.quizapp.service.impl;

import com.sameh.quizapp.dao.QuestionDao;
import com.sameh.quizapp.dao.QuizDao;
import com.sameh.quizapp.exception.ApiNotFoundException;
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
    QuizDao quizDao;
    @Autowired
    QuestionDao questionDao;

    @Override
    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        logger.info("=========== inside createQuiz method ==========");
        if(numQ<=0){
            logger.warn("Cannot create quiz, number of questions must be greater than 0");
            throw new ApiNotFoundException("A quiz must contain one or more questions");
        }

        List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numQ);
        if(questions.isEmpty()){
            logger.error("Cannot create quiz, no questions found in category: {}", category);
            throw new ApiNotFoundException("Not Found Questions in this category: "+category);
        }


        if(questions.size()<numQ){
            logger.error("Cannot create quiz, there are only {} questions in {} category and you choose {}", questions.size(), category, numQ);
            throw new ApiNotFoundException("There are only "+ questions.size() +" questions in "+ category +" category and you choose "+numQ);
        }

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);

        logger.info("Quiz created successfully");
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        logger.info("========== inside getQuizQuestions method =========");
        Optional<Quiz> quiz = quizDao.findById(id);
        if(quiz.isEmpty()){
            logger.warn("Cannot get quiz, quiz with id: {} not found", id);
            throw new ApiNotFoundException("not found quiz with this id: "+id);
        }

        List<Question> questionsFromDb = quiz.get().getQuestions();
        List<QuestionWrapper> questionsForUsers = new ArrayList<>();
        for (Question q : questionsFromDb){
            QuestionWrapper qw = new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
            questionsForUsers.add(qw);
        }
        return new ResponseEntity<>(questionsForUsers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        logger.info("========== inside calculateResult method =========");

        Optional<Quiz> quiz = quizDao.findById(id);
        if(quiz.isEmpty()){
            logger.warn("Cannot calculate results, quiz with id: {} not found", id);
            throw new ApiNotFoundException("not found quiz with this id: "+id);
        }

        List<Question> questions = quiz.get().getQuestions();
        int right = 0;
        int i = 0;
        for(Response response : responses){
            if(response.getResponse().equals(questions.get(i).getRightAnswer())){
                right++;
            }
            i++;
        }

        logger.info("Quiz submitted successfully and the result is {}",right);
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
