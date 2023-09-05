package com.sameh.quizapp.controller;

import com.sameh.quizapp.model.Question;
import com.sameh.quizapp.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionService questionService;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestion(){
        logger.info("========== inside getAllQuestion method ===========");
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category){
        logger.info("============ inside getQuestionsByCategory method =============");
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        logger.info("========== inside addQuestion method ===========");
        return questionService.addQuestion(question);
    }

    @PutMapping("update/{id}")
    public String updateQuestion(@PathVariable Integer id, @RequestBody Question question){
        logger.info("=========== inside updateQuestion method ============");
        return questionService.updateQuestion(id,question);
    }

    @DeleteMapping("delete/{id}")
    public String deleteQuestion(@PathVariable Integer id ){
        logger.info("============= inside deleteQuestion method ==============");
        return questionService.deleteQuestion(id);
    }

}
