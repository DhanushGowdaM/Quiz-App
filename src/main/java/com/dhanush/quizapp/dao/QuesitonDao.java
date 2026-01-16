package com.dhanush.quizapp.dao;

import com.dhanush.quizapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuesitonDao extends JpaRepository<Question, Integer> {

    List<Question> findByCategoryIgnoreCase(String category);

    @Query(value = "SELECT * FROM question q WHERE q.category=:category ORDER BY RANDOM() LIMIT :numQ", nativeQuery = true)
    List<Question> findRandomQuesitonsByCategory(String category, int numQ);
}
