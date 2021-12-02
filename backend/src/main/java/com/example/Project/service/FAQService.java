package com.example.Project.service;

import com.example.Project.domain.Question;
import com.example.Project.domain.QuestionVote;
import com.example.Project.domain.Response;
import com.example.Project.domain.Vote;
import com.example.Project.repository.QuestionRepository;
import com.example.Project.repository.QuestionVoteRepository;
import com.example.Project.repository.ResponseRepository;
import com.example.Project.repository.VoteRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class FAQService {

    private final QuestionRepository questionRepository;

    private final ResponseRepository responseRepository;

    private final VoteRepository voteRepository;

    private final QuestionVoteRepository questionVoteRepository;

    public FAQService(QuestionRepository questionRepository,
                      ResponseRepository responseRepository,
                      VoteRepository voteRepository,
                      QuestionVoteRepository questionVoteRepository) {
        this.questionRepository = questionRepository;
        this.responseRepository = responseRepository;
        this.voteRepository = voteRepository;
        this.questionVoteRepository = questionVoteRepository;
    }

    public Question saveQuestion(Question question) {

        question.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        return questionRepository.save(question);
    }

    public Question findQuestionById(int id) {
        return questionRepository.findQuestionById(id);
    }

    public List<Question> findAllQuestions() {
        return questionRepository.findAll();
    }

    public List<Question> findQuestionsByCreatedBy(String createdBy) {
        return questionRepository.findQuestionsByCreatedBy(createdBy);
    }

    public Response saveResponse(Response response, int questionId) {

        Question question = this.findQuestionById(questionId);
        response.setQuestion(question);
        response.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        return responseRepository.save(response);
    }

    public List<Response> findResponsesByQuestionId(int questionId) {
        return responseRepository.findResponsesByQuestionId(questionId);
    }

    public List<Response> findResponsesByCreatedBy(String createdBy) {
        return responseRepository.findResponsesByCreatedBy(createdBy);
    }

    public Vote saveVoteResponse(Vote vote, String voteRequest, int responseId) {
        Vote isPresent = voteRepository.findVoteByResponseIdAndCreatedBy(responseId, vote.getCreatedBy());
        if (isPresent == null) {
            Response response = responseRepository.findResponseById(responseId);
            vote.setResponse(response);
            if (voteRequest.equals("up")) {
                vote.setVoteUp(1);
                vote.setVoteDown(0);
            } else if (voteRequest.equals("down")) {
                vote.setVoteUp(0);
                vote.setVoteDown(1);
            }
            Vote voteResult = voteRepository.save(vote);
            responseRepository.updateResponseVotes(responseId);
            return voteResult;
        } else {
            return isPresent;
        }
    }

    public void updateVoteResponse(String responseRequest, int responseId, String voteCreator) {
        if (responseRequest.equals("up")) {
            voteRepository.updateResponseVoteUp(responseId, voteCreator);
            responseRepository.updateResponseVotes(responseId);
        } else if (responseRequest.equals("down")) {
            voteRepository.updateResponseVoteDown(responseId, voteCreator);
            responseRepository.updateResponseVotes(responseId);
        }
    }

    public void acceptResponse(int responseId, String questionCreatedBy) {
        Response response = responseRepository.findResponseById(responseId);
        Response responseAccepted = responseRepository.findAcceptedResponse(response.getQuestion().getId());
        if (response.getQuestion().getCreatedBy().equals(questionCreatedBy)) {
            if (responseAccepted != null) {
                responseRepository.updateAcceptResponse(responseAccepted.getId(), false);
            }
            responseRepository.updateAcceptResponse(responseId, true);
        }

    }

    public QuestionVote saveQuestionVote(QuestionVote questionVote, String voteRequest, int questionId) {
        QuestionVote isPresent = questionVoteRepository
                .findQuestionVoteByQuestionIdAndCreatedBy(questionId, questionVote.getCreatedBy());
        if (isPresent == null) {
            Question question = questionRepository.findQuestionById(questionId);
            questionVote.setQuestion(question);
            if (voteRequest.equals("up")) {
                questionVote.setVoteUp(1);
                questionVote.setVoteDown(0);
            } else if (voteRequest.equals("down")) {
                questionVote.setVoteUp(0);
                questionVote.setVoteDown(1);
            }
            QuestionVote voteResult = questionVoteRepository.save(questionVote);
            questionRepository.updateQuestionVotes(questionId);
            return voteResult;
        }
        return isPresent;
    }

    public void updateQuestionVote(String voteRequest, int questionId, String voteCreator) {
        if (voteRequest.equals("up")) {
            questionVoteRepository.updateQuestionVoteUp(questionId, voteCreator);
            questionRepository.updateQuestionVotes(questionId);
        } else if (voteRequest.equals("down")) {
            questionVoteRepository.updateQuestionVoteDown(questionId, voteCreator);
            questionRepository.updateQuestionVotes(questionId);
        }
    }

}
