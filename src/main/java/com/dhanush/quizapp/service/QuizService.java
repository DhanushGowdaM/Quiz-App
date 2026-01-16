package com.dhanush.quizapp.service;

import com.dhanush.quizapp.dao.QuesitonDao;
import com.dhanush.quizapp.dao.QuizDao;
import com.dhanush.quizapp.model.Question;
import com.dhanush.quizapp.model.QuestionWrapper;
import com.dhanush.quizapp.model.Quiz;
import com.dhanush.quizapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;

    @Autowired
    QuesitonDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        try{
            List<Question> questions = questionDao.findRandomQuesitonsByCategory(category, numQ);

            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setQuestions(questions);

            quizDao.save(quiz);

            return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("FAILED", HttpStatus.NOT_ACCEPTABLE);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        try{
            Optional<Quiz> quiz = quizDao.findById(id);
            List<Question> questionsFromDb = quiz.get().getQuestions();

            List<QuestionWrapper> questionsForUser = new ArrayList<>();
            for(Question q : questionsFromDb){
                QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
                questionsForUser.add(qw);
            }

            return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questions = quiz.get().getQuestions();

        int score = 0;
        int i = 0;
        for(Response response : responses){
            if(response.getResponse().equals(questions.get(i++).getRightAnswer()))score++;
        }
        return new ResponseEntity<>(score, HttpStatus.OK);
    }
}
