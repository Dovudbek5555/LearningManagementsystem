package com.example.test_system.controller;

import com.example.test_system.entity.Group;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.RatingDto;
import com.example.test_system.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RestController
@RequestMapping("/rating")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PreAuthorize("hasRole('ROLE_TEACHER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/byExam/{examId}")
    public HttpEntity<ApiResponse> getTopStudentsRating(@PathVariable Integer examId){
        ApiResponse apiResponse = ratingService.getTopStudentsByExam(examId);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    @GetMapping("/byDate")
    public HttpEntity<ApiResponse> getTopStudentRatingByDate(@RequestBody RatingDto ratingDto){
        ApiResponse apiResponse = ratingService.getTopStudentByDate(ratingDto);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }

    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    @GetMapping("/byGroup/{groupId}")
    public HttpEntity<ApiResponse> getTopStudentRatingByGroup(@PathVariable Integer groupId){
        ApiResponse topStudentsByGroup = ratingService.getTopStudentsByGroup(groupId);
        return ResponseEntity.ok(topStudentsByGroup);
    }

}
