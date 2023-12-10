package com.sameh.quizapp.Repository;


import com.sameh.quizapp.entity.Question;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    QuestionRepository questionRepository;

    private Question question;
    List<Question> res;

    @AfterEach
    public void tearDown(){
        questionRepository.deleteAll();
    }

    @BeforeEach
    public void setUp(){
         question = new Question(
                "What is the capital of France?",
                "Berlin",
                "Paris",
                "Madrid",
                "Rome",
                "Paris",
                "Medium",
                "Geography"
        );

        questionRepository.save(question);

        question = new Question(
                "Which programming language is known for its 'Write Once, Run Anywhere' principle?",
                "Java",
                "Python",
                "C++",
                "JavaScript",
                "Java",
                "Easy",
                "Programming"
        );

        questionRepository.save(question);
    }

    @DisplayName("Find By Category Test")
    @Test
    public void TestFindByCategory() {
        res = questionRepository.findByCategory("Geography");

        assertEquals(1, res.size(), "the size should be 1");
        assertEquals("What is the capital of France?", res.get(0).getQuestionTitle(),
                "should be What is the capital of France?");
    }

    @DisplayName("Find By Question Title")
    @Test
    public void TestFindByQuestionTitle() {
        res = questionRepository.findByQuestionTitle("Which programming language is known for its 'Write Once, Run Anywhere' principle?");

        assertEquals(1, res.size(), "size should be 1");
    }

    @DisplayName("Find Random Questions By Category")
    @Test
    public void TestFindRandomQuestionsByCategory() {
        res = questionRepository.findRandomQuestionsByCategory("Programming", 1);

        assertEquals(1, res.size(), "should be 1");
        assertEquals(0, questionRepository.findRandomQuestionsByCategory(
                "bla", 5).size());
    }
}