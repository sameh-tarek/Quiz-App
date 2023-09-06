package com.sameh.quizapp.dao;

import com.sameh.quizapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionDao extends
        JpaRepository<Question,Integer>{
    List<Question> findByCategory(String category);
    List<Question> findByQuestionTitle(String questionTitle);

    @Query(value = "SELECT * FROM question q WHERE q.category = :category ORDER BY q.id LIMIT :numQ", nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(String category, int numQ);
}
