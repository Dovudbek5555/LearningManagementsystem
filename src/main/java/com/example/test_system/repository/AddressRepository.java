package com.example.test_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.test_system.Entity.Address;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "address", path = "list")
public interface AddressRepository extends JpaRepository<Address, Integer> {
}
