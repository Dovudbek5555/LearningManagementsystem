package com.example.test_system.repository;

import com.example.test_system.entity.Exam;
import com.example.test_system.entity.Group;
import com.example.test_system.entity.Result;
import com.example.test_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Integer> {
    List<Result> findAllByExam_Id(Integer examId);

}
