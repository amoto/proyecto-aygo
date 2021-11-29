package com.example.Project.controller;

import com.example.Project.domain.Question;
import com.example.Project.domain.Response;
import com.example.Project.domain.Vote;
import com.example.Project.service.FAQService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faq")
public class FAQController {

    private final FAQService faqService;

    public FAQController(FAQService faqService){
        this.faqService = faqService;
    }

    @GetMapping("/question/{id}")
    public ResponseEntity<Question> getQuestion(@PathVariable("id") int id){
        return new ResponseEntity<>(faqService.findQuestionById(id),HttpStatus.OK);
    }

    @GetMapping("/questions")
    public ResponseEntity<List<Question>> getAllQuestion(){
        return new ResponseEntity<>(faqService.findAllQuestions(),HttpStatus.OK);
    }

    @PostMapping("/question")
    public ResponseEntity<Question> saveQuestion(@RequestBody Question question){
        return new ResponseEntity<>(faqService.saveQuestion(question), HttpStatus.OK);
    }

    @GetMapping("/questions/createdBy/{createdBy}")
    public ResponseEntity<List<Question>> getQuestionsByCreatedBy(@PathVariable("createdBy") String createdBy){
        return new ResponseEntity<>(faqService.findQuestionsByCreatedBy(createdBy),
                HttpStatus.OK);
    }

    @GetMapping("/responses/question/{questionId}")
    public ResponseEntity<List<Response>> getResponsesByQuestionId(@PathVariable("questionId") int questionId){
        return new ResponseEntity<>(faqService.findResponsesByQuestionId(questionId),
                HttpStatus.OK);
    }

    @PostMapping("/response/question/{questionId}")
    public ResponseEntity<Response> saveResponse(@RequestBody Response response,
                                                 @PathVariable int questionId){
        return new ResponseEntity<>(faqService.saveResponse(response,questionId),HttpStatus.OK);
    }

    @GetMapping("/responses/createdBy/{createdBy}")
    public ResponseEntity<List<Response>> getResponsesByCreatedBy(@PathVariable("createdBy") String createdBy){
        return new ResponseEntity<>(faqService.findResponsesByCreatedBy(createdBy),
                HttpStatus.OK);
    }

    @PostMapping("/voteUp/response/{responseId}")
    public ResponseEntity<Vote> saveVoteUpResponse(@RequestBody Vote vote,
                                                 @PathVariable("responseId") int responseId){
        return new ResponseEntity<>(faqService.saveVoteResponse(vote,"up",responseId),
                HttpStatus.OK);
    }

    @PostMapping("/voteDown/response/{responseId}")
    public ResponseEntity<Vote> saveVoteDownResponse(@RequestBody Vote vote,
                                                   @PathVariable("responseId") int responseId){
        return new ResponseEntity<>(faqService.saveVoteResponse(vote,"down",responseId),
                HttpStatus.OK);
    }

    @PutMapping("/voteUp/response/{responseId}/voteCreator/{voteCreator}")
    public ResponseEntity<Void> updateVoteUpResponse(@PathVariable("responseId") int responseId,
                                                     @PathVariable("voteCreator") String voteCreator){
        faqService.updateVoteResponse("up",responseId,voteCreator);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/voteDown/response/{responseId}/voteCreator/{voteCreator}")
    public ResponseEntity<Void> updateVoteDownResponse(@PathVariable("responseId") int responseId,
                                                     @PathVariable("voteCreator") String voteCreator){
        faqService.updateVoteResponse("down",responseId,voteCreator);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
