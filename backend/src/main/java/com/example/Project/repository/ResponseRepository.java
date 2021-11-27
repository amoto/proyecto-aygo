package com.example.Project.repository;

import com.example.Project.domain.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ResponseRepository extends JpaRepository<Response,Long> {

    @Override
    Response save(Response response);

    Response findResponseById(int responseId);

    List<Response> findResponsesByQuestionId(int questionId);

    List<Response> findResponsesByCreatedBy(String createdBy);

    @Transactional
    @Modifying
    @Query(value = "with sum_votes as(" +
            "select sum(vote_up) as sumUp, sum(vote_down) as sumDown from votes where response_id = :responseId)" +
            "update responses set up_votes = sum_votes.sumUp, down_votes = sum_votes.sumDown from sum_votes where id = :responseId",
            nativeQuery = true)
    void updateResponseVotes(@Param("responseId") int responseId);

}
