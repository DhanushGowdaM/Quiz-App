package com.dhanush.quizapp.dao;

import com.dhanush.quizapp.model.Question;
import com.dhanush.quizapp.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizDao extends JpaRepository<Quiz, Integer> {
}
