package com.sameh.quizapp.service.impl;

import com.sameh.quizapp.controller.QuestionController;
import com.sameh.quizapp.dao.QuestionDao;
import com.sameh.quizapp.exception.ApiNotFoundException;
import com.sameh.quizapp.model.Question;
import com.sameh.quizapp.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);

    @Autowired
    private QuestionDao questionDao;

    @Override
    public ResponseEntity<List<Question>> getAllQuestions() {
        logger.info("========== inside getAllQuestions method ==========");

        List<Question> questions = questionDao.findAll();
        if(questions.isEmpty()){
            logger.warn("Not Found Any Question");
            throw new ApiNotFoundException("Not Found Any Question");
        }

        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        logger.info("========== inside getQuestionsByCategory method ==========");

        List<Question> questions = questionDao.findByCategory(category);
        if(questions.isEmpty()){
            logger.warn("Not Found Any Question in this Category: {}", category);
            throw new ApiNotFoundException("Not Found Any Question in this Category: "+ category);
        }

        return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> addQuestion(Question question) {
        logger.info("========== inside addQuestion method ==========");

        try {
             questionDao.save(question);
             logger.info("Question added successfully");
             return new ResponseEntity<>("success",HttpStatus.CREATED);
        }catch (Exception e){
            logger.error("Failed to add the question", e);
            e.printStackTrace();
        }

           return new ResponseEntity<>("Failed",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public String updateQuestion(Integer id, Question question) {
        logger.info("========== inside updateQuestion method ==========");

        if(questionDao.findById(id).isEmpty()){
            logger.warn("Not Found Questions with this id = {}", id);
            throw new ApiNotFoundException("Not Found Questions with this id = "+id);
        }

        questionDao.save(question);
        logger.info("Question updated successfully");
        return "success";
    }

    @Override
    public String deleteQuestion(Integer id) {
        logger.info("========== inside deleteQuestion method ==========");

        if(questionDao.findById(id).isEmpty()){
            logger.warn("Not Found Question with Id you want to delete it: {}", id);
            throw new ApiNotFoundException("Not Found Question with Id you want to delete it: "+id);
        }

        questionDao.deleteById(id);
        logger.info("Question deleted successfully");
        return "success";
    }
}
