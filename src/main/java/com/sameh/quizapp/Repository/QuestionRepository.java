package com.sameh.quizapp.Repository;

import com.sameh.quizapp.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends
        JpaRepository<Question,Integer>{
    List<Question> findByCategory(String category);
    List<Question> findByQuestionTitle(String questionTitle);

    @Query(value = "SELECT * FROM question q WHERE q.category = :category ORDER BY q.id LIMIT :numQ", nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(String category, int numQ);
}
