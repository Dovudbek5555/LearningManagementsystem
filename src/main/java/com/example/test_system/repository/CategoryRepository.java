package com.example.test_system.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.test_system.Entity.Category;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "category", path = "list")
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
