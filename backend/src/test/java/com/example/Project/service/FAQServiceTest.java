package com.example.Project.service;

import com.example.Project.GetTestObjects;
import com.example.Project.domain.Question;
import com.example.Project.domain.Response;
import com.example.Project.domain.Vote;
import com.example.Project.repository.QuestionRepository;
import com.example.Project.repository.ResponseRepository;
import com.example.Project.repository.VoteRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FAQService.class)
public class FAQServiceTest {

    @Autowired
    private FAQService faqService;

    @MockBean
    private QuestionRepository questionRepository;

    @MockBean
    private ResponseRepository responseRepository;

    @MockBean
    private VoteRepository voteRepository;

    private GetTestObjects getTestObjects;

    private Question question;

    private Response response;

    private Vote vote;

    @Before
    public void setUp(){
        getTestObjects = new GetTestObjects();
        question = getTestObjects.getQuestion();
        response = getTestObjects.getResponse();
        vote = getTestObjects.getVote();
    }

    @Test
    public void saveQuestion_ok() {

        when(questionRepository.save(any()))
                .thenReturn(question);

        Question questionSaved = faqService.saveQuestion(question);

        Assert.assertEquals(questionSaved.getId(),question.getId());
        Assert.assertEquals(questionSaved.getCreatedBy(),question.getCreatedBy());
        Assert.assertEquals(questionSaved.getCreatedAt(),question.getCreatedAt());
        Assert.assertEquals(questionSaved.getDescription(),question.getDescription());
        Assert.assertEquals(questionSaved.getTags(),question.getTags());
    }

    @Test
    public void findQuestionById_ok(){

        when(questionRepository.findQuestionById(1))
                .thenReturn(question);

        Question questionResult = faqService.findQuestionById(1);

        Assert.assertEquals(questionResult.getDescription(),question.getDescription());

    }

    @Test
    public void findAllQuestions_ok(){

        List<Question> questions = new ArrayList<>();
        questions.add(question);

        when(questionRepository.findAll())
                .thenReturn(questions);

        List<Question> questionsResult = faqService.findAllQuestions();

        Assert.assertEquals(questionsResult.get(0),question);

    }

    @Test
    public void findQuestionsByCreatedBy_ok(){

        List<Question> questions = new ArrayList<>();
        questions.add(question);

        when(questionRepository.findQuestionsByCreatedBy(question.getCreatedBy()))
                .thenReturn(questions);

        List<Question> questionsResult = faqService.findQuestionsByCreatedBy(question.getCreatedBy());

        Assert.assertEquals(questionsResult.get(0).getCreatedBy(),question.getCreatedBy());
    }

    @Test
    public void saveResponse_ok(){

        when(questionRepository.findQuestionById(1))
                .thenReturn(question);
        when(responseRepository.save(any()))
                .thenReturn(response);

        Response responseSaved = faqService.saveResponse(response,question.getId());

        Assert.assertEquals(responseSaved.getCreatedAt(),response.getCreatedAt());
        Assert.assertEquals(responseSaved.getCreatedBy(), responseSaved.getCreatedBy());
        Assert.assertEquals(responseSaved.getQuestion(),question);

    }

    @Test
    public void findResponsesByQuestionId_ok(){

        List<Response> responses = new ArrayList<>();
        responses.add(response);

        when(responseRepository.findResponsesByQuestionId(1))
                .thenReturn(responses);

        List<Response> responsesResult = faqService.findResponsesByQuestionId(1);

        Assert.assertEquals(responsesResult.get(0),response);

    }

    @Test
    public void findResponsesByCreatedBy_ok(){

        List<Response> responses = new ArrayList<>();
        responses.add(response);

        when(responseRepository.findResponsesByCreatedBy(response.getCreatedBy()))
                .thenReturn(responses);

        List<Response> responsesResult = faqService.findResponsesByCreatedBy(response.getCreatedBy());

        Assert.assertEquals(responsesResult.get(0).getCreatedBy(),response.getCreatedBy());

    }

    @Test
    public void saveVoteUpResponse_ok(){

        when(voteRepository.findVoteByResponseIdAndCreatedBy(response.getId(),vote.getCreatedBy()))
                .thenReturn(null);
        when(responseRepository.findResponseById(response.getId()))
                .thenReturn(response);
        when(voteRepository.save(any()))
                .thenReturn(vote);
        Mockito.doNothing().when(responseRepository)
                .updateResponseVotes(response.getId());

        Vote voteUpResult = faqService.saveVoteResponse(vote,"up",response.getId());

        Assert.assertEquals(voteUpResult.getVoteUp(),1);
        Assert.assertEquals(voteUpResult.getVoteDown(),0);
        Assert.assertEquals(voteUpResult.getResponse(),response);

    }

    @Test
    public void saveVoteDownResponse_ok(){

        when(voteRepository.findVoteByResponseIdAndCreatedBy(response.getId(),vote.getCreatedBy()))
                .thenReturn(null);
        when(responseRepository.findResponseById(response.getId()))
                .thenReturn(response);
        when(voteRepository.save(any()))
                .thenReturn(vote);
        Mockito.doNothing().when(responseRepository)
                .updateResponseVotes(response.getId());

        Vote voteUpResult = faqService.saveVoteResponse(vote,"down",response.getId());

        Assert.assertEquals(voteUpResult.getVoteUp(),0);
        Assert.assertEquals(voteUpResult.getVoteDown(),1);
        Assert.assertEquals(voteUpResult.getResponse(),response);

    }

    @Test
    public void saveVoteUpResponse_voteExistDoNothing(){

        when(voteRepository.findVoteByResponseIdAndCreatedBy(response.getId(),vote.getCreatedBy()))
                .thenReturn(vote);

        faqService.saveVoteResponse(vote,"up",response.getId());

        verify(voteRepository,times(0))
                .save(any());

    }

    @Test
    public void updateVoteResponseUp_ok(){

        doNothing().when(voteRepository)
                .updateResponseVoteUp(response.getId(),vote.getCreatedBy());
        faqService.updateVoteResponse("up",response.getId(),vote.getCreatedBy());

        verify(responseRepository,times(1))
                .updateResponseVotes(response.getId());

    }

    @Test
    public void updateVoteResponseDown_ok(){

        doNothing().when(voteRepository)
                .updateResponseVoteUp(response.getId(),vote.getCreatedBy());
        faqService.updateVoteResponse("down",response.getId(),vote.getCreatedBy());

        verify(responseRepository,times(1))
                .updateResponseVotes(response.getId());

    }

}
