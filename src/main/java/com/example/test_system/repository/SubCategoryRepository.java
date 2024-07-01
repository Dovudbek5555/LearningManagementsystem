package com.example.test_system.repository;

import com.example.test_system.entity.SubCategory;
import jakarta.validation.constraints.DecimalMax;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {
    boolean existsByName(String name);
   List<SubCategory> findAllByCategory_Id(Integer categoryId);
}
