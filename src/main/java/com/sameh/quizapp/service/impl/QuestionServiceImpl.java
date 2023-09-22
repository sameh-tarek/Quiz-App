package com.sameh.quizapp.service.impl;

import com.sameh.quizapp.Repository.QuestionRepository;
import com.sameh.quizapp.dto.QuestionDto;
import com.sameh.quizapp.exception.DuplicateRecordException;
import com.sameh.quizapp.exception.MissingServletRequestParameterException;
import com.sameh.quizapp.exception.NoUpdateFoundException;
import com.sameh.quizapp.exception.RecordNotFoundException;
import com.sameh.quizapp.mappper.QuestionMapper;
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
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public ResponseEntity<List<QuestionDto>> getAllQuestions() {
        logger.info("Admin wants to get ِAll Questions");
        List<Question> questions = questionRepository.findAll();
        if (questions.isEmpty()) {
            logger.warn("No questions found.");
            throw new RecordNotFoundException("No questions found.");
        }

        List<QuestionDto> questionDtos = new ArrayList<>();
        for(Question question : questions){
            QuestionDto dto = questionMapper.mapToDto(question);
            questionDtos.add(dto);
        }

        logger.info("Retrieved all questions {}", questionDtos);
        return new ResponseEntity<>(questionDtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<QuestionDto>> getQuestionsByCategory(String category) {
        logger.info("Admin wants to get All Questions in {} category",category);
        List<Question> questions = questionRepository.findByCategory(category);
        if (questions.isEmpty()) {
            logger.warn("No questions found in category: {}",category);
            throw new RecordNotFoundException("No questions found in category: " + category);
        }

        List<QuestionDto> questionDtos = new ArrayList<>();
        for(Question question : questions){
            QuestionDto dto = questionMapper.mapToDto(question);
            questionDtos.add(dto);
        }

        logger.info("Retrieved questions by category: {} and this is the questions {}", category, questionDtos);
        return new ResponseEntity<>(questionDtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> addQuestion(QuestionDto questionDto) {
        logger.info("Admin wants to add new Question");
        List<Question> questions = questionRepository.findByQuestionTitle(questionDto.getQuestionTitle());
        if (!questions.isEmpty()) {
            logger.warn("Question with title: {} is already exists." , questionDto.getQuestionTitle());
            throw new DuplicateRecordException("Question with title: " + questionDto.getQuestionTitle() + " is already exists.");
        }
        Question question = questionMapper.mapToEntity(questionDto);
        questionRepository.save(question);
        logger.info("Added question: {}", questionDto);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    @Override
    public String updateQuestion(Integer id, QuestionDto questionDto) {
        logger.info("Admin wants to Update Question wit id {}",id);
        Question existingQuestion = questionRepository.findById(id).orElseThrow(()
                -> new RecordNotFoundException("No question found with ID: " + id));

        if (questionDto.getQuestionTitle() == null) {
            logger.warn("Question title cannot be empty");
            throw new MissingServletRequestParameterException("Question title cannot be empty");
        }

        Question question = questionMapper.mapToEntity(questionDto);
        questionRepository.save(question);
        if(existingQuestion == question){
            logger.warn("not found any update");
            throw new NoUpdateFoundException("not found any update");
        }

        logger.info("Updated question with ID: {}", id);
        logger.info("question before update {}", existingQuestion);
        logger.info("question after update {}", questionRepository.findById(id));
        return "success";
    }

    @Override
    public String deleteQuestion(Integer id) {
        logger.info("Admin wants to Delete Question with id {}",id);
        Optional<Question> existingQuestion = questionRepository.findById(id);
        if(existingQuestion.isEmpty()){
            logger.warn("No question found with ID: {}", id);
            throw  new RecordNotFoundException("No question found with ID: {}" + id);
        }

        logger.info("This is the question he want to delete it {}", existingQuestion);

        questionRepository.deleteById(id);
        logger.info("Deleted question with ID {}", id);
        return "success";
    }
}