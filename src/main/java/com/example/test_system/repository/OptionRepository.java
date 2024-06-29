package com.example.test_system.repository;

import com.example.test_system.entity.Option;
import com.example.test_system.entity.Question;
import com.example.test_system.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "option", path = "list")
public interface OptionRepository extends JpaRepository<Option, Integer> {
//    boolean existsByOptionEnum(String optionEnum);
}
