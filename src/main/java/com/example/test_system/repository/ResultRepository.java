package com.example.test_system.repository;

import com.example.test_system.entity.Exam;
import com.example.test_system.entity.Group;
import com.example.test_system.entity.Result;
import com.example.test_system.entity.User;
import com.example.test_system.payload.RatingBySumCorrectCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ResultRepository extends JpaRepository<Result, Integer> {
    List<Result> findAllByExam_IdOrderByCorrectCountDesc(Integer examId);

    @Query("SELECT RatingBySumCorrectCount(r.student, SUM(r.correctCount)) " +
            "FROM Result r " +
            "WHERE r.exam IN :exams " +
            "GROUP BY r.student " +
            "ORDER BY SUM(r.correctCount) DESC")
    List<RatingBySumCorrectCount> findStudentCorrectCountsByExams(@Param("exams") List<Exam> exams);

    List<Result> findAllByCheckedIsFalse();

    @Query("SELECT r FROM Result r " +
            "JOIN r.exam e " +
            "WHERE r.checked = false " +
            "AND e.group = :group")
    List<Result> findUncheckedResultsByGroup(@Param("group") Group group);
}
