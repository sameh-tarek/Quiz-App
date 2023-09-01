package com.sameh.quizapp.dao;

import com.sameh.quizapp.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizDao extends
        JpaRepository<Quiz,Integer> {
}
