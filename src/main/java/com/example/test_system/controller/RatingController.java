package com.example.test_system.controller;

import com.example.test_system.payload.ApiResponse;
import com.example.test_system.service.RatingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/rating")
public class RatingController {

    private RatingService ratingService;

    @GetMapping("/byExam/{examId}")
    public ApiResponse getTopStudentsRating(@PathVariable Integer examId){

    }

}
