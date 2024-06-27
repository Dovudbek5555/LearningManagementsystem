package com.example.test_system.repository;

import com.example.test_system.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "address", path = "list")
public interface AddressRepository extends JpaRepository<Address, Integer> {
}
