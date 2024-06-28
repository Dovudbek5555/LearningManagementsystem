package com.example.test_system.repository;

import com.example.test_system.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Integer> {
    boolean existsByName(String name);
}
