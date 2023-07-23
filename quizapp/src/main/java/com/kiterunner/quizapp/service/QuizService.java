package com.kiterunner.quizapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kiterunner.quizapp.dao.QuestionDao;
import com.kiterunner.quizapp.dao.QuizDao;
import com.kiterunner.quizapp.dto.Question;
import com.kiterunner.quizapp.dto.QuestionWrapper;
import com.kiterunner.quizapp.dto.Quiz;
import com.kiterunner.quizapp.dto.Response;

@Service
public class QuizService {

	@Autowired
	QuizDao quizDao;
	
	@Autowired
	QuestionDao questionDao;
	
	public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
		
		List<Question> questions = questionDao.findRandomQuestionsByCategory(category,numQ);
		
		Quiz quiz = new Quiz();
		quiz.setTitle(title);
		quiz.setQuestions(questions);
		quizDao.save(quiz);
		
		return new ResponseEntity<>("Success",HttpStatus.CREATED);
	}

	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
		Optional<Quiz> quiz = quizDao.findById(id);
		List<Question> questionFromDB = quiz.get().getQuestions();
		List<QuestionWrapper> questionForUser = new ArrayList<>();
		for(Question q : questionFromDB) {
			QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(),q.getOption4());
			questionForUser.add(qw);
		}
		
		return new ResponseEntity<List<QuestionWrapper>>(questionForUser,HttpStatus.OK);
	}

	public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
		Quiz quiz = quizDao.findById(id).get();
		List<Question> questions = quiz.getQuestions();
		int right = 0;
		int i=0;
		for(Response response:responses) {
			if(response.getResponse().equals(questions.get(i++).getRightAnswer()))
				right++;
		}
		
		return new ResponseEntity<Integer>(right,HttpStatus.OK);
	}
	
	 
}
