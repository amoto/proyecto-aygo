package com.example.Project.repository;

import com.example.Project.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {

    @Override
    Question save(Question question);

    Question findQuestionById(int id);
}
