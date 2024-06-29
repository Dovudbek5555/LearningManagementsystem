package com.example.test_system.repository;

import com.example.test_system.entity.Option;
import com.example.test_system.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "option", path = "list")
public interface OptionRepository extends JpaRepository<Option, Integer> {
    Integer countByQuestion(Question question);
}
