package com.example.Project.repository;

import com.example.Project.domain.Response;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponseRepository extends JpaRepository<Response,Long> {

    @Override
    Response save(Response response);

    List<Response> findResponsesByQuestionId(int questionId);

}
