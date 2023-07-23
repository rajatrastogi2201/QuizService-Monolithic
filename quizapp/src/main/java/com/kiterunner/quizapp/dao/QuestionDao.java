package com.kiterunner.quizapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kiterunner.quizapp.dto.Question;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer>{
	
	List<Question> findByCategory(String category);
	
	@Query(value="SELECT * FROM question q where q.category=:category ORDER BY RANDOM() LIMIT :numQ", nativeQuery=true)
	List<Question> findRandomQuestionsByCategory(String category, int numQ);

}
