package com.example.test_system.repository;

import com.example.test_system.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    boolean existsByQuestion(String question);

    default List<Question> findAllByDifficulty(String difficulty) {
        return null;
    }
}
