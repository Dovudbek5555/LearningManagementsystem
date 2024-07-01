package com.example.test_system.repository;

import com.example.test_system.entity.Question;
import com.example.test_system.entity.enums.DifficultyEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    boolean existsByQuestion(String question);

    List<Question> findAllByDifficulty(DifficultyEnum difficulty);
    List<Question> findAllBySubCategory_Id(Integer subCategoryId);
}
