package com.example.test_system.repository;

import com.example.test_system.Entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Integer> {
}
