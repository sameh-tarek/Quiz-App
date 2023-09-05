package com.sameh.quizapp.service.impl;

import com.sameh.quizapp.dao.QuestionDao;
import com.sameh.quizapp.dao.QuizDao;
import com.sameh.quizapp.exception.ApiNotFoundException;
import com.sameh.quizapp.model.Question;
import com.sameh.quizapp.model.QuestionWrapper;
import com.sameh.quizapp.model.Quiz;
import com.sameh.quizapp.model.Response;
import com.sameh.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    QuizDao quizDao;
    @Autowired
    QuestionDao questionDao;

    @Override
    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        if(numQ<=0){
            throw new ApiNotFoundException("A quiz must contain one or more questions");
        }

        List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numQ);
        if(questions.isEmpty())
            throw new ApiNotFoundException("Not Found Questions in this category: "+category);

        if(questions.size()<numQ){
            throw new ApiNotFoundException("There are only "+ questions.size() +" questions in "+ category +" category and you choose "+numQ);
        }

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        if(quiz.isEmpty())
            throw new ApiNotFoundException("not found quiz with this id: "+id);

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
        Optional<Quiz> quiz = quizDao.findById(id);
        if(quiz.isEmpty())
            throw new ApiNotFoundException("not found quiz with this id: "+id);

        List<Question> questions = quiz.get().getQuestions();
        int right = 0;
        int i = 0;
        for(Response response : responses){
            if(response.getResponse().equals(questions.get(i).getRightAnswer())){
                right++;
            }
            i++;
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
