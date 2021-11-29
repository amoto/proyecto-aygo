package com.example.Project;

import com.example.Project.domain.Question;
import com.example.Project.domain.QuestionVote;
import com.example.Project.domain.Response;
import com.example.Project.domain.Vote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetTestObjects {

    public GetTestObjects(){}

    Logger LOGGER = LoggerFactory.getLogger(GetTestObjects.class);

    public Question getQuestion(){
        Question question = new Question();
        try{
            question.setId(1);
            question.setTitle("First question test!");
            question.setDescription("First question description!");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date parsedDate = dateFormat.parse("2021-11-25 12:00:00.000");
            question.setCreatedAt(new Timestamp(parsedDate.getTime()));
            question.setCreatedBy("First User");
            question.setTags("Java,Python,Go,Kotlin");
            return question;
        }catch (ParseException ex){
            LOGGER.error("Error parsing date from string {}",ex.getMessage());
        }
        return question;
    }

    public Response getResponse(){
        Response response = new Response();
        try{
            response.setId(1);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date parsedDate = dateFormat.parse("2021-11-25 12:00:00.000");
            response.setCreatedAt(new Timestamp(parsedDate.getTime()));
            response.setCreatedBy("Second User");
            response.setText("First response for first question!");
            response.setDownVotes(0);
            response.setUpVotes(0);
            return response;
        }catch (ParseException ex){
            LOGGER.error("Error parsing date from string {}",ex.getMessage());
        }
        return response;
    }

    public Response getResponseAccepted(){
        Response response = new Response();
        response.setAccepted(true);
        return response;
    }

    public Vote getVote(){
        Vote vote = new Vote();
        vote.setCreatedBy("First User");
        return vote;
    }

    public QuestionVote getQuestionVote(){
        QuestionVote questionVote = new QuestionVote();
        questionVote.setCreatedBy("Second User");
        return questionVote;
    }

}
