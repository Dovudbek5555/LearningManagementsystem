package com.example.test_system.repository;

import com.example.test_system.entity.Exam;
import com.example.test_system.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Integer> {
}
