package com.example.test_system.repository;

import com.example.test_system.entity.*;
import com.example.test_system.payload.RatingBySumCorrectCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Integer> {
    List<Result> findAllByExam_IdOrderByCorrectCountDesc(Integer examId);

    @Query("SELECT new com.example.test_system.payload.RatingBySumCorrectCount(r.student, SUM(r.correctCount)) " +
            "FROM Result r WHERE r.exam IN :exams GROUP BY r.student ORDER BY SUM(r.correctCount) DESC")
    List<RatingBySumCorrectCount> findStudentCorrectCountsByExams(List<Exam> exams);

@Query("SELECT new com.example.test_system.payload.RatingBySumCorrectCount(r.student, SUM(r.correctCount)) " +
        "FROM Result r WHERE r.student IN :user GROUP BY r.student ORDER BY SUM(r.correctCount) DESC")
    List<RatingBySumCorrectCount> findStudentCorrectCountsByGroups(@Param("user") List<User> users);


    List<Result> findAllByCheckedIsFalse();

    Result findByAnswerContains(Answer answer);

}
