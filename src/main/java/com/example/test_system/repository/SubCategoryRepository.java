package com.example.test_system.repository;

import com.example.test_system.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {
    boolean existsByName(String name);


}
