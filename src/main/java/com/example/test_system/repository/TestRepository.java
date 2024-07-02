package com.example.test_system.repository;

import com.example.test_system.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TestRepository extends JpaRepository<Test, Integer> {
    List<Test> findByTeacherId(UUID teacherId);
}
