package com.sameh.quizapp.Repository;

import com.sameh.quizapp.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends
        JpaRepository<Quiz,Integer> {
}
