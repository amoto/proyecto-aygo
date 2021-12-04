package com.example.Project.repository;

import com.example.Project.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Override
    Question save(Question question);

    Question findQuestionById(int id);

    List<Question> findQuestionsByCreatedBy(String createdBy);

    @Transactional
    @Modifying
    @Query(value = "WITH sum_votes AS(" +
            "SELECT SUM(vote_up) AS sumUp, SUM(vote_down) AS sumDown FROM questionVotes WHERE question_id = :questionId)" +
            "UPDATE questions SET votes_up = sum_votes.sumUp, votes_down = sum_votes.sumDown FROM sum_votes WHERE id = :questionId",
            nativeQuery = true)
    void updateQuestionVotes(@Param("questionId") int questionId);

}
