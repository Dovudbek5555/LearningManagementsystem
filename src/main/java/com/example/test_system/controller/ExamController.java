package com.example.test_system.controller;

import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.ExamDto;
import com.example.test_system.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exam")
public class ExamController {
    private final ExamService examService;

    @PostMapping
    public HttpEntity<ApiResponse> saveExam(@RequestBody ExamDto examDto) {
        ApiResponse apiResponse = examService.saveExam(examDto);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }

    @GetMapping
    public HttpEntity<ApiResponse> getAllExams() {
        ApiResponse apiResponse = examService.getAllExams();
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }

    @GetMapping("/{id}")
    public HttpEntity<ApiResponse> getExamById(@PathVariable Integer id) {
        ApiResponse apiResponse = examService.getExamById(id);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }

    @PutMapping
    public HttpEntity<ApiResponse> updateExam(@RequestBody ExamDto examDto) {
        ApiResponse apiResponse = examService.updateExam(examDto);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<ApiResponse> deleteExam(@PathVariable Integer id) {
        ApiResponse apiResponse = examService.deleteExamById(id);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }
}
