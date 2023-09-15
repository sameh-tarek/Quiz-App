package com.sameh.quizapp.service.impl;

import com.sameh.quizapp.dao.QuestionDao;
import com.sameh.quizapp.exception.DuplicateRecordException;
import com.sameh.quizapp.exception.MissingServletRequestParameterException;
import com.sameh.quizapp.exception.NoUpdateFoundException;
import com.sameh.quizapp.exception.RecordNotFoundException;
import com.sameh.quizapp.model.Question;
import com.sameh.quizapp.service.QuestionService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);

    @Autowired
    private QuestionDao questionDao;

    @Override
    public ResponseEntity<List<Question>> getAllQuestions() {
        logger.info("Admin wants to get ِAll Questions");
        List<Question> questions = questionDao.findAll();
        if (questions.isEmpty()) {
            logger.warn("No questions found.");
            throw new RecordNotFoundException("No questions found.");
        }

        logger.info("Retrieved all questions {}", questions);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        logger.info("Admin wants to get All Questions in {} category",category);
        List<Question> questions = questionDao.findByCategory(category);
        if (questions.isEmpty()) {
            logger.warn("No questions found in category: {}",category);
            throw new RecordNotFoundException("No questions found in category: " + category);
        }

        logger.info("Retrieved questions by category: {} and this is the questions {}", category, questions);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> addQuestion(Question question) {
        logger.info("Admin wants to add new Question");
        List<Question> questions = questionDao.findByQuestionTitle(question.getQuestionTitle());
        if (!questions.isEmpty()) {
            logger.warn("Question with title: {} is already exists." , question.getQuestionTitle());
            throw new DuplicateRecordException("Question with title: " + question.getQuestionTitle() + " is already exists.");
        }

        questionDao.save(question);
        logger.info("Added question: {}", question);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    @Override
    public String updateQuestion(Integer id, Question question) {
        logger.info("Admin wants to Update Question wit id {}",id);
        Question existingQuestion = questionDao.findById(id).orElseThrow(()
                -> new RecordNotFoundException("No question found with ID: " + id));

        if (question.getQuestionTitle() == null) {
            logger.warn("Question title cannot be empty");
            throw new MissingServletRequestParameterException("Question title cannot be empty");
        }

        questionDao.save(question);
        if(existingQuestion == question){
            logger.warn("not found any update");
            throw new NoUpdateFoundException("not found any update");
        }

        logger.info("Updated question with ID: {}", id);
        logger.info("question before update {}", existingQuestion);
        logger.info("question after update {}", questionDao.findById(id));
        return "success";
    }

    @Override
    public String deleteQuestion(Integer id) {
        logger.info("Admin wants to Delete Question with id {}",id);
        Optional<Question> existingQuestion = questionDao.findById(id);
        if(existingQuestion.isEmpty()){
            logger.warn("No question found with ID: {}", id);
            throw  new RecordNotFoundException("No question found with ID: {}" + id);
        }

        logger.info("This is the question he want to delete it {}", existingQuestion);

        questionDao.deleteById(id);
        logger.info("Deleted question with ID {}", id);
        return "success";
    }
}