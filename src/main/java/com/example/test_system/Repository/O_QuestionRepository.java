package com.example.test_system.Repository;

import com.example.test_system.Entity.O_Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface O_QuestionRepository extends JpaRepository<O_Question, Integer> {
}
