package com.sameh.quizapp.service.impl;

import com.sameh.quizapp.dao.QuestionDao;
import com.sameh.quizapp.exception.ApiNotFoundException;
import com.sameh.quizapp.model.Question;
import com.sameh.quizapp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionDao questionDao;

    @Override
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questions = questionDao.findAll();
        if(questions.isEmpty())
            throw new ApiNotFoundException("Not Found Any Question");

        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        List<Question> questions = questionDao.findByCategory(category);
        if(questions.isEmpty())
            throw new ApiNotFoundException("Not Found Any Question in this Category: "+ category);

        return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> addQuestion(Question question) {
            try {
                questionDao.save(question);
                return new ResponseEntity<>("success",HttpStatus.CREATED);
            }catch (Exception e){
                e.printStackTrace();
            }
           return new ResponseEntity<>("Failed",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public String updateQuestion(Integer id, Question question) {
        if(questionDao.findById(id).isEmpty())
            throw new ApiNotFoundException("Not Found Questions with this id = "+id);

        questionDao.save(question);
        return "success";
    }

    @Override
    public String deleteQuestion(Integer id) {
        if(questionDao.findById(id).isEmpty())
            throw new ApiNotFoundException("Not Found Question with Id you want to delete it: "+id);

        questionDao.deleteById(id);
        return "success";
    }
}
