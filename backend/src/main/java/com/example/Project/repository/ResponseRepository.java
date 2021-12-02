package com.example.Project.repository;

import com.example.Project.domain.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ResponseRepository extends JpaRepository<Response, Long> {

    @Override
    Response save(Response response);

    Response findResponseById(int responseId);

    @Query("select r from responses r order by (r.upVotes - r.downVotes) desc ")
    List<Response> findResponsesByQuestionId(int questionId);

    List<Response> findResponsesByCreatedBy(String createdBy);

    @Transactional
    @Modifying
    @Query(value = "WITH sum_votes AS(" +
            "SELECT SUM(vote_up) AS sumUp, SUM(vote_down) AS sumDown FROM votes WHERE response_id = :responseId)" +
            "UPDATE responses SET up_votes = sum_votes.sumUp, down_votes = sum_votes.sumDown FROM sum_votes WHERE id = :responseId",
            nativeQuery = true)
    void updateResponseVotes(@Param("responseId") int responseId);

    @Query("select r from responses r join questions q on r.question.id = q.id where r.accepted = true")
    Response findAcceptedResponse(@Param("questionId") int questionId);

    @Transactional
    @Modifying
    @Query("update responses r set r.accepted = :accepted where r.id = :responseId")
    void updateAcceptResponse(@Param("responseId") int responseId, @Param("accepted") boolean accepted);

}
