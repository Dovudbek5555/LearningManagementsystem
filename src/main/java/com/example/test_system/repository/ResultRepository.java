package com.example.test_system.repository;

import com.example.test_system.entity.Exam;
import com.example.test_system.entity.Group;
import com.example.test_system.entity.Result;
import com.example.test_system.entity.User;
import com.example.test_system.payload.RatingBySumCorrectCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Integer> {
    List<Result> findAllByExam_IdOrderByCorrectCountDesc(Integer examId);

    @Query("SELECT RatingBySumCorrectCount(r.student, SUM(r.correctCount)) " +
            "FROM Result r " +
            "WHERE r.exam IN :exams " +
            "GROUP BY r.student " +
            "ORDER BY SUM(r.correctCount) DESC")
    List<RatingBySumCorrectCount> findStudentCorrectCountsByExams(@Param("exams") List<Exam> exams);
}
