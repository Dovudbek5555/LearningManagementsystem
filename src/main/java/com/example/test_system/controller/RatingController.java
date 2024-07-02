package com.example.test_system.controller;

import com.example.test_system.entity.Group;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController("/rating")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    @GetMapping("/byExam/{examId}")
    public HttpEntity<ApiResponse> getTopStudentsRating(@PathVariable Integer examId){
        ApiResponse apiResponse = ratingService.getTopStudentsByExam(examId);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    @GetMapping("/byDate")
    public HttpEntity<ApiResponse> getTopStudentRatingByDate(@RequestBody LocalDate startDate, LocalDate finishDate){
        ApiResponse apiResponse = ratingService.getTopStudentByDate(startDate, finishDate);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    @GetMapping("/byGroup")
    public HttpEntity<ApiResponse> getTopStudentRatingByGroup(@RequestParam Integer group){
        ApiResponse topStudentsByGroup = ratingService.getTopStudentsByGroup(group);
        return ResponseEntity.ok(topStudentsByGroup);
    }

}
