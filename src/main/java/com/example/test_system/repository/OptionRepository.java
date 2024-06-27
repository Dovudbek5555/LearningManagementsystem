package com.example.test_system.repository;

import com.example.test_system.Entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "option", path = "list")
public interface OptionRepository extends JpaRepository<Option, Integer> {
}
