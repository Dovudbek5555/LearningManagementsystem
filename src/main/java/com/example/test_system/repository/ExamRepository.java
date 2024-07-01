package com.example.test_system.repository;

import com.example.test_system.entity.Exam;
import com.example.test_system.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Integer> {
    @Query("SELECT e FROM Exam e WHERE e.startDate >= :startDate AND e.finishDate <= :endDate")
    List<Exam> findExamsBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
