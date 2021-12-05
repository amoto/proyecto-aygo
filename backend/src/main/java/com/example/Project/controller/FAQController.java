package com.example.Project.controller;

import com.example.Project.domain.Question;
import com.example.Project.domain.QuestionVote;
import com.example.Project.domain.Response;
import com.example.Project.domain.Vote;
import com.example.Project.service.FAQService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faq")
public class FAQController {

    private final Boolean securityEnabled;

    private final FAQService faqService;

    public FAQController(@Value("${security.enabled:true}") Boolean securityEnabled,
                         FAQService faqService) {
        this.securityEnabled = securityEnabled;
        this.faqService = faqService;
    }

    @GetMapping("/question/{id}")
    public ResponseEntity<Question> getQuestion(@PathVariable("id") int id) {
        return new ResponseEntity<>(faqService.findQuestionById(id), HttpStatus.OK);
    }

    @GetMapping("/questions")
    public ResponseEntity<List<Question>> getAllQuestion() {
        return new ResponseEntity<>(faqService.findAllQuestions(), HttpStatus.OK);
    }

    @PostMapping("/question")
    public ResponseEntity<Question> saveQuestion(@RequestBody Question question) {
        question.setCreatedBy(getUser());
        return new ResponseEntity<>(faqService.saveQuestion(question), HttpStatus.OK);
    }

    @GetMapping("/questions/")
    public ResponseEntity<List<Question>> getQuestionsByCreatedBy() {
        String createdBy = getUser();
        return new ResponseEntity<>(faqService.findQuestionsByCreatedBy(createdBy),
                HttpStatus.OK);
    }

    @GetMapping("/responses/question/{questionId}")
    public ResponseEntity<List<Response>> getResponsesByQuestionId(@PathVariable("questionId") int questionId) {
        return new ResponseEntity<>(faqService.findResponsesByQuestionId(questionId),
                HttpStatus.OK);
    }

    @PostMapping("/response/question/{questionId}")
    public ResponseEntity<Response> saveResponse(@RequestBody Response response,
                                                 @PathVariable int questionId) {
        response.setCreatedBy(getUser());
        return new ResponseEntity<>(faqService.saveResponse(response, questionId), HttpStatus.OK);
    }

    @GetMapping("/responses")
    public ResponseEntity<List<Response>> getResponsesByCreatedBy() {
        String createdBy = getUser();
        return new ResponseEntity<>(faqService.findResponsesByCreatedBy(createdBy),
                HttpStatus.OK);
    }

    @PostMapping("/voteUp/question/{questionId}")
    public ResponseEntity<QuestionVote> saveVoteUpQuestion(@RequestBody QuestionVote questionVote,
                                                           @PathVariable("questionId") int questionId) {
        questionVote.setCreatedBy(getUser());
        return new ResponseEntity<>(faqService.saveQuestionVote(questionVote, "up", questionId),
                HttpStatus.OK);
    }

    @PostMapping("/voteDown/question/{questionId}")
    public ResponseEntity<QuestionVote> saveVoteDownQuestion(@RequestBody QuestionVote questionVote,
                                                             @PathVariable("questionId") int questionId) {
        questionVote.setCreatedBy(getUser());
        return new ResponseEntity<>(faqService.saveQuestionVote(questionVote, "down", questionId),
                HttpStatus.OK);
    }

    @PutMapping("/voteUp/question/{questionId}")
    public ResponseEntity<Void> updateVoteUpQuestion(@PathVariable("questionId") int questionId) {
        String voteCreator = getUser();
        faqService.updateQuestionVote("up", questionId, voteCreator);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/voteDown/question/{questionId}")
    public ResponseEntity<Void> updateVoteDownQuestion(@PathVariable("questionId") int questionId) {
        String voteCreator = getUser();
        faqService.updateQuestionVote("down", questionId, voteCreator);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/voteUp/response/{responseId}")
    public ResponseEntity<Vote> saveVoteUpResponse(@RequestBody Vote vote,
                                                   @PathVariable("responseId") int responseId) {
        vote.setCreatedBy(getUser());
        return new ResponseEntity<>(faqService.saveVoteResponse(vote, "up", responseId),
                HttpStatus.OK);
    }

    @PostMapping("/voteDown/response/{responseId}")
    public ResponseEntity<Vote> saveVoteDownResponse(@RequestBody Vote vote,
                                                     @PathVariable("responseId") int responseId) {
        vote.setCreatedBy(getUser());
        return new ResponseEntity<>(faqService.saveVoteResponse(vote, "down", responseId),
                HttpStatus.OK);
    }

    @PutMapping("/voteUp/response/{responseId}")
    public ResponseEntity<Void> updateVoteUpResponse(@PathVariable("responseId") int responseId) {
        String voteCreator = getUser();
        faqService.updateVoteResponse("up", responseId, voteCreator);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/voteDown/response/{responseId}")
    public ResponseEntity<Void> updateVoteDownResponse(@PathVariable("responseId") int responseId) {
        String voteCreator = getUser();
        faqService.updateVoteResponse("down", responseId, voteCreator);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/accepted/response/{responseId}")
    public ResponseEntity<Void> acceptResponse(@PathVariable("responseId") int responseId) {
        String questionCreatedBy = getUser();
        faqService.acceptResponse(responseId, questionCreatedBy);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public String getUser() {
        return securityEnabled ?
                ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getClaimAsString("cognito:username") : "test_user";
    }

}
