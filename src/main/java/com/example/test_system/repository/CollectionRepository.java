package com.example.test_system.repository;

import com.example.test_system.entity.QuestionList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<QuestionList, Integer> {
}
