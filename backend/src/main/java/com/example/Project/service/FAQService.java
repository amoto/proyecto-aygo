package com.example.Project.service;

import com.example.Project.domain.Question;
import com.example.Project.domain.Response;
import com.example.Project.repository.QuestionRepository;
import com.example.Project.repository.ResponseRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class FAQService {

    private final QuestionRepository questionRepository;

    private final ResponseRepository responseRepository;

    public FAQService(QuestionRepository questionRepository,
                      ResponseRepository responseRepository){
        this.questionRepository = questionRepository;
        this.responseRepository = responseRepository;
    }

    public Question saveQuestion(Question question){

        question.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        return questionRepository.save(question);
    }

    public Question findQuestionById(int id){
        return questionRepository.findQuestionById(id);
    }

    public List<Question> findAllQuestions(){
        return questionRepository.findAll();
    }

    public Response saveResponse(Response response, int questionId){

        Question question = this.findQuestionById(questionId);
        response.setQuestion(question);
        response.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        return responseRepository.save(response);
    }

    public List<Response> findResponsesByQuestionId(int questionId){
        return responseRepository.findResponsesByQuestionId(questionId);
    }

}
