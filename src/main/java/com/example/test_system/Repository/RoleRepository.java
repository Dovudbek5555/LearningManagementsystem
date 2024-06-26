package com.example.test_system.Repository;

import com.example.test_system.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "role", path = "list")
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
