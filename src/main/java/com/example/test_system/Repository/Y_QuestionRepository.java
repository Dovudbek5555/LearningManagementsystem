package com.example.test_system.Repository;

import com.example.test_system.Entity.Y_Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

public interface Y_QuestionRepository extends JpaRepository<Y_Question, Integer> {

}
