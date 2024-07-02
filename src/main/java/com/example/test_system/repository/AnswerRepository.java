package com.example.test_system.repository;

import com.example.test_system.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "answer", collectionResourceRel = "list")
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}
