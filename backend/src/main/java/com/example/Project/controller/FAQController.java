package com.example.Project.controller;

import com.example.Project.domain.Question;
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
        return new ResponseEntity<>(faqService.findQuestionById(id), HttpStatus.ACCEPTED);
    }

    @GetMapping("/question")
    public ResponseEntity<List<Question>> getAllQuestion() {
        return new ResponseEntity<>(faqService.findAllQuestions(), HttpStatus.ACCEPTED);
    }

    @PostMapping("/question")
    public ResponseEntity<Question> saveQuestion(@RequestBody Question question) {
        return new ResponseEntity<>(faqService.saveQuestion(question), HttpStatus.ACCEPTED);
    }

    @GetMapping("/questions/")
    public ResponseEntity<List<Question>> getQuestionsByCreatedBy() {
        String createdBy = getUser();
        return new ResponseEntity<>(faqService.findQuestionsByCreatedBy(createdBy),
                HttpStatus.ACCEPTED);
    }

    @GetMapping("/responses/question/{questionId}")
    public ResponseEntity<List<Response>> getResponsesByQuestionId(@PathVariable("questionId") int questionId) {
        return new ResponseEntity<>(faqService.findResponsesByQuestionId(questionId),
                HttpStatus.ACCEPTED);
    }

    @PostMapping("/response/question/{questionId}")
    public ResponseEntity<Response> saveResponse(@RequestBody Response response,
                                                 @PathVariable int questionId) {
        response.setCreatedBy(getUser());
        return new ResponseEntity<>(faqService.saveResponse(response, questionId), HttpStatus.ACCEPTED);
    }

    @GetMapping("/responses")
    public ResponseEntity<List<Response>> getResponsesByCreatedBy() {
        String createdBy = getUser();
        return new ResponseEntity<>(faqService.findResponsesByCreatedBy(createdBy),
                HttpStatus.ACCEPTED);
    }

    @PostMapping("/voteUp/response/{responseId}")
    public ResponseEntity<Vote> saveVoteUpResponse(@RequestBody Vote vote,
                                                   @PathVariable("responseId") int responseId) {
        vote.setCreatedBy(getUser());
        return new ResponseEntity<>(faqService.saveVoteResponse(vote, "up", responseId),
                HttpStatus.ACCEPTED);
    }

    @PostMapping("/voteDown/response/{responseId}")
    public ResponseEntity<Vote> saveVoteDownResponse(@RequestBody Vote vote,
                                                     @PathVariable("responseId") int responseId) {
        vote.setCreatedBy(getUser());
        return new ResponseEntity<>(faqService.saveVoteResponse(vote, "down", responseId),
                HttpStatus.ACCEPTED);
    }

    @PutMapping("/voteUp/response/{responseId}")
    public ResponseEntity<Void> updateVoteUpResponse(@PathVariable("responseId") int responseId) {
        String voteCreator = getUser();
        faqService.updateVoteResponse("up", responseId, voteCreator);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/voteDown/response/{responseId}")
    public ResponseEntity<Void> updateVoteDownResponse(@PathVariable("responseId") int responseId) {
        String voteCreator = getUser();
        faqService.updateVoteResponse("down", responseId, voteCreator);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    public String getUser() {
        return securityEnabled ?
        ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getClaimAsString("cognito:username") : "test_user";
    }

}
