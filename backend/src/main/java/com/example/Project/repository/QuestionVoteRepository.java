package com.example.Project.repository;

import com.example.Project.domain.QuestionVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface QuestionVoteRepository extends JpaRepository<QuestionVote, Long> {

    @Transactional
    @Modifying
    @Query("update questionvotes qv set qv.voteUp = 1, qv.voteDown = 0 where qv.question.id = :questionId and qv.createdBy = :voteCreator")
    void updateQuestionVoteUp(@Param("questionId") int questionId,
                              @Param("voteCreator") String voteCreator);

    @Transactional
    @Modifying
    @Query("update questionvotes qv set qv.voteUp = 0, qv.voteDown = 1 where qv.question.id = :questionId and qv.createdBy = :voteCreator")
    void updateQuestionVoteDown(@Param("questionId") int questionId,
                                @Param("voteCreator") String voteCreator);

    QuestionVote findQuestionVoteByQuestionIdAndCreatedBy(int questionId, String createdBy);

}
