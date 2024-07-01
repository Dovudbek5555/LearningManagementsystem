package com.example.test_system.repository;

import com.example.test_system.entity.User;
import com.example.test_system.entity.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumberAndIdNot(String phoneNumber, UUID id);

    List<User> findAllByGroup_Id(Integer id);

    List<User> findAllByRoleEnum(RoleEnum roleEnum);
}
