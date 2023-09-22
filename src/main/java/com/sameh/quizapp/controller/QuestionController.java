package com.sameh.quizapp.controller;

import com.sameh.quizapp.dto.QuestionDto;
import com.sameh.quizapp.mappper.QuestionMapper;
import com.sameh.quizapp.model.Question;
import com.sameh.quizapp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("allQuestions")
    public ResponseEntity<List<QuestionDto>> getAllQuestion(){
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<QuestionDto>> getQuestionsByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody QuestionDto questionDto){
        return questionService.addQuestion(questionDto);
    }

    @PutMapping("update/{id}")
    public String updateQuestion(@PathVariable Integer id, @RequestBody QuestionDto questionDto){
        return questionService.updateQuestion(id,questionDto);
    }

    @DeleteMapping("delete/{id}")
    public String deleteQuestion(@PathVariable Integer id ){
        return questionService.deleteQuestion(id);
    }

}
