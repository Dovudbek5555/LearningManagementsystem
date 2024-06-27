package com.example.test_system.Repository;

import com.example.test_system.Entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}
