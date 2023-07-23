package com.kiterunner.quizapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kiterunner.quizapp.dto.Quiz;

@Repository
public interface QuizDao extends JpaRepository<Quiz, Integer>{

}
