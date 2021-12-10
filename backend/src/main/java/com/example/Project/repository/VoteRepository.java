package com.example.Project.repository;

import com.example.Project.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Transactional
    @Modifying
    @Query("update votes v set v.voteUp = 1, v.voteDown = 0 where v.response.id = :responseId and v.createdBy = :voteCreator")
    void updateResponseVoteUp(@Param("responseId") int responseId,
                              @Param("voteCreator") String voteCreator);

    @Transactional
    @Modifying
    @Query("update votes v set v.voteUp = 0, v.voteDown = 1 where v.response.id = :responseId and v.createdBy = :voteCreator")
    void updateResponseVoteDown(@Param("responseId") int responseId,
                                @Param("voteCreator") String voteCreator);

    Vote findVoteByResponseIdAndCreatedBy(int responseId, String createdBy);

}
