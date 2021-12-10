package com.example.Project.controller;

import com.example.Project.GetTestObjects;
import com.example.Project.domain.Question;
import com.example.Project.domain.QuestionVote;
import com.example.Project.domain.Response;
import com.example.Project.domain.Vote;
import com.example.Project.service.FAQService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FAQController.class)
public class FAQControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FAQService faqService;

    private Question question;
    private Response response;
    private Vote vote;
    private QuestionVote questionVote;
    private ObjectMapper mapper;

    @Before
    public void setUp(){
        mapper = new ObjectMapper();
        GetTestObjects getTestObjects = new GetTestObjects();
        question = getTestObjects.getQuestion();
        response = getTestObjects.getResponse();
        vote = getTestObjects.getVote();
        questionVote = getTestObjects.getQuestionVote();
    }

    @Test
    public void getQuestion_ok() throws Exception {

        when(faqService.findQuestionById(1))
                .thenReturn(question);

        mvc.perform(MockMvcRequestBuilders.get("/faq/question/{id}",1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.created_by",is(question.getCreatedBy())).isString());

    }

    @Test
    public void getAllQuestion_ok() throws Exception {
        List<Question> questions = new ArrayList<>();
        questions.add(question);

        when(faqService.findAllQuestions())
                .thenReturn(questions);

        mvc.perform(MockMvcRequestBuilders.get("/faq/questions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]",is(question)).isNotEmpty());
    }

    @Test
    public void saveQuestion_ok() throws Exception {

        when(faqService.saveQuestion(any(Question.class)))
                .thenReturn(question);

        mvc.perform(MockMvcRequestBuilders.post("/faq/question")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(question)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.created_by",is(question.getCreatedBy())).isString());
    }

    @Test
    public void getQuestionsByCreatedBy_ok() throws Exception {
        List<Question> questions = new ArrayList<>();
        questions.add(question);

        when(faqService.findQuestionsByCreatedBy(question.getCreatedBy()))
                .thenReturn(questions);

        mvc.perform(MockMvcRequestBuilders.get("/faq/questions/createdBy/{createdBy}",question.getCreatedBy()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]",is(question)).isNotEmpty());

    }

    @Test
    public void getResponsesByQuestionId_ok() throws Exception {
        List<Response> responses = new ArrayList<>();
        responses.add(response);

        when(faqService.findResponsesByQuestionId(1))
                .thenReturn(responses);

        mvc.perform(MockMvcRequestBuilders.get("/faq/responses/question/{questionId}",1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].text",is(response.getText())).isString());

    }

    @Test
    public void saveResponse_ok() throws Exception {

        when(faqService.saveResponse(any(Response.class),eq(1)))
                .thenReturn(response);

        mvc.perform(MockMvcRequestBuilders.post("/faq/response/question/{questionId}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(response)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text",is(response.getText())).isString());

    }

    @Test
    public void getResponsesByCreatedBy_ok() throws Exception {
        List<Response> responses = new ArrayList<>();
        responses.add(response);

        when(faqService.findResponsesByCreatedBy(response.getCreatedBy()))
                .thenReturn(responses);

        mvc.perform(MockMvcRequestBuilders.get("/faq/responses/createdBy/{createdBy}",response.getCreatedBy()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].text",is(response.getText())).isString());

    }

    @Test
    public void saveVoteUpResponse_ok() throws Exception {
        vote.setVoteUp(1);
        vote.setVoteDown(0);

        when(faqService.saveVoteResponse(any(Vote.class),eq("up"),eq(response.getId())))
                .thenReturn(vote);

        mvc.perform(MockMvcRequestBuilders.post("/faq/voteUp/response/{responseId}",response.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(vote)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vote_up",is(1)).isNumber())
                .andExpect(jsonPath("$.vote_down",is(0)).isNumber())
                .andExpect(jsonPath("$.created_by",is(vote.getCreatedBy())).isString());

    }

    @Test
    public void saveVoteDownResponse_ok() throws Exception {
        vote.setVoteUp(0);
        vote.setVoteDown(1);

        when(faqService.saveVoteResponse(any(Vote.class),eq("down"),eq(response.getId())))
                .thenReturn(vote);

        mvc.perform(MockMvcRequestBuilders.post("/faq/voteDown/response/{responseId}",response.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(vote)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vote_up",is(0)).isNumber())
                .andExpect(jsonPath("$.vote_down",is(1)).isNumber())
                .andExpect(jsonPath("$.created_by",is(vote.getCreatedBy())).isString());

    }

    @Test
    public void updateVoteUpResponse_ok() throws Exception {

        Mockito.doNothing().when(faqService)
                .updateVoteResponse("up",response.getId(),vote.getCreatedBy());

        mvc.perform(MockMvcRequestBuilders.put("/faq/voteUp/response/{responseId}/voteCreator/{voteCreator}",
                response.getId(),vote.getCreatedBy()))
                .andExpect(status().isOk());
    }

    @Test
    public void updateVoteDownResponse_ok() throws Exception {

        Mockito.doNothing().when(faqService)
                .updateVoteResponse("down",response.getId(),vote.getCreatedBy());

        mvc.perform(MockMvcRequestBuilders.put("/faq/voteDown/response/{responseId}/voteCreator/{voteCreator}",
                        response.getId(),vote.getCreatedBy()))
                .andExpect(status().isOk());
    }

    @Test
    public void acceptResponse_ok() throws Exception {

        Mockito.doNothing().when(faqService)
                .acceptResponse(response.getId(),question.getCreatedBy());

        mvc.perform(MockMvcRequestBuilders
                .put("/faq/accepted/response/{responseId}/questionCreatedBy/{questionCreatedBy}",
                        response.getId(),question.getCreatedBy()))
                .andExpect(status().isOk());

    }

    @Test
    public void saveVoteUpQuestion_ok() throws Exception {

        when(faqService.saveQuestionVote(any(QuestionVote.class),eq("up"),eq(question.getId())))
                .thenReturn(questionVote);

        mvc.perform(MockMvcRequestBuilders.post("/faq/voteUp/question/{questionId}",question.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(questionVote)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.created_by",is(questionVote.getCreatedBy())).isString());
    }

    @Test
    public void saveVoteDownQuestion_ok() throws Exception {

        when(faqService.saveQuestionVote(any(QuestionVote.class),eq("down"),eq(question.getId())))
                .thenReturn(questionVote);

        mvc.perform(MockMvcRequestBuilders.post("/faq/voteDown/question/{questionId}",question.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(questionVote)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.created_by",is(questionVote.getCreatedBy())).isString());
    }

    @Test
    public void updateVoteUpQuestion_ok() throws Exception {

        Mockito.doNothing().when(faqService)
                .updateQuestionVote("up",question.getId(),questionVote.getCreatedBy());

        mvc.perform(MockMvcRequestBuilders.put("/faq/voteUp/question/{questionId}/voteCreator/{voteCreator}",
                question.getId(),questionVote.getCreatedBy()))
                .andExpect(status().isOk());

    }

    @Test
    public void updateVoteDownQuestion_ok() throws Exception {

        Mockito.doNothing().when(faqService)
                .updateQuestionVote("down",question.getId(),questionVote.getCreatedBy());

        mvc.perform(MockMvcRequestBuilders.put("/faq/voteDown/question/{questionId}/voteCreator/{voteCreator}",
                        question.getId(),questionVote.getCreatedBy()))
                .andExpect(status().isOk());

    }

}
