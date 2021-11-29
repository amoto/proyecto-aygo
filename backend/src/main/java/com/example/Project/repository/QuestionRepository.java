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
public interface QuestionRepository extends JpaRepository<Question,Long> {

    @Override
    Question save(Question question);

    Question findQuestionById(int id);

    List<Question> findQuestionsByCreatedBy(String createdBy);

    @Transactional
    @Modifying
    @Query(value = "with sum_votes as(" +
            "select sum(vote_up) as sumUp, sum(vote_down) as sumDown from questionVotes where question_id = :questionId)" +
            "update questions set votes_up = sum_votes.sumUp, votes_down = sum_votes.sumDown from sum_votes where id = :questionId",
            nativeQuery = true)
    void updateQuestionVotes(@Param("questionId") int questionId);

}
