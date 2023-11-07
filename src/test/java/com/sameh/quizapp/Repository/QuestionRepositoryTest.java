package com.sameh.quizapp.Repository;

import com.sameh.quizapp.entity.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository underTest;

    @Test
    void findByCategory() {
        //Arrange
        Question question = new Question(
                "What is the capital of France?",
                "Paris",
                "London",
                "Rome",
                "Berlin",
                "Paris",
                "Easy",
                "Geography"
        );
        underTest.save(question);

        //Act
        List<Question> questionList = underTest.findByCategory("Geography");

        //Assert
        assertThat(questionList).isNotNull();
        assertThat(questionList.size()).isEqualTo(1);
    }

    @Test
    void findByQuestionTitle() {
        //Arrange
        Question question = new Question(
                "What is the capital of France?",
                "Paris",
                "London",
                "Rome",
                "Berlin",
                "Paris",
                "Easy",
                "Geography"
        );
        underTest.save(question);

        //Act
        List<Question> questionList = underTest.findByQuestionTitle("What is the capital of France?");

        //Assert
        assertThat(questionList).isNotNull();
        assertThat(questionList.size()).isEqualTo(1);
    }

    @Test
    void findRandomQuestionsByCategory() {
        //Arrange
        Question question1 = new Question(
                "What is the capital of Spain?",
                "Madrid",
                "Barcelona",
                "Valencia",
                "Seville",
                "Madrid",
                "Easy",
                "Geography"
        );

        Question question2 = new Question(
                "What is the capital of Italy?",
                "Rome",
                "Milan",
                "Naples",
                "Florence",
                "Rome",
                "Easy",
                "Geography"
        );

        Question question3 = new Question(
                "What is the capital of Germany?",
                "Berlin",
                "Munich",
                "Frankfurt",
                "Hamburg",
                "Berlin",
                "Easy",
                "Geography"
        );

        Question question4 = new Question(
                "Who was the first president of the United States?",
                "George Washington",
                "Thomas Jefferson",
                "Abraham Lincoln",
                "Franklin D. Roosevelt",
                "George Washington",
                "Easy",
                "History"
        );
        underTest.saveAll(Arrays.asList(question1,question2,question3,question4));

        //Act
        List<Question> questionList = underTest.findRandomQuestionsByCategory("Geography",2);

        //Assert
        assertThat(questionList).isNotNull();
        assertThat(questionList.size()).isEqualTo(2);
    }
}