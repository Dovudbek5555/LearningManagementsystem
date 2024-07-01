package com.example.test_system.repository;

import com.example.test_system.entity.Group;
import com.example.test_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.UUID;

public interface GroupRepository extends JpaRepository<Group, Integer> {
    boolean existsByName(String name);
    List<Group> findAllByTeacherId_Id(UUID teacherId);
    List<Group> findAllByCategory_Id(Integer categoryId);
}
