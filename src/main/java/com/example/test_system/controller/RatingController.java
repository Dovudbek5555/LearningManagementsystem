package com.example.test_system.controller;

import com.example.test_system.payload.ApiResponse;
import com.example.test_system.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController("/rating")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @GetMapping("/byExam/{examId}")
    public HttpEntity<ApiResponse> getTopStudentsRating(@PathVariable Integer examId){
        ApiResponse apiResponse = ratingService.getTopStudentsByExam(examId);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/byDate")
    public HttpEntity<ApiResponse> getTopStudentRatingByDate(@RequestBody LocalDate startDate, LocalDate finishDate){
        ApiResponse apiResponse = ratingService.getTopStudentByDate(startDate, finishDate);
        return ResponseEntity.ok(apiResponse);
    }

}
