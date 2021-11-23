package com.example.Project.controller;

import com.example.Project.domain.Question;
import com.example.Project.domain.Response;
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
        return new ResponseEntity<>(faqService.findQuestionById(id),HttpStatus.ACCEPTED);
    }

    @GetMapping("/question")
    public ResponseEntity<List<Question>> getAllQuestion(){
        return new ResponseEntity<>(faqService.findAllQuestions(),HttpStatus.ACCEPTED);
    }

    @PostMapping("/question")
    public ResponseEntity<Question> saveQuestion(@RequestBody Question question){
        return new ResponseEntity<>(faqService.saveQuestion(question), HttpStatus.ACCEPTED);
    }

    @GetMapping("/responses/{questionId}")
    public ResponseEntity<List<Response>> getResponsesByQuestionId(@PathVariable("questionId") int questionId){
        return new ResponseEntity<>(faqService.findResponsesByQuestionId(questionId),
                HttpStatus.ACCEPTED);
    }

    @PostMapping("/response/{questionId}")
    public ResponseEntity<Response> saveResponse(@RequestBody Response response,
                                                 @PathVariable int questionId){
        return new ResponseEntity<>(faqService.saveResponse(response,questionId),HttpStatus.ACCEPTED);
    }

}
