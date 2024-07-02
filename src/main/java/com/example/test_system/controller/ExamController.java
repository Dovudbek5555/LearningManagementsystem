package com.example.test_system.controller;

import com.example.test_system.entity.User;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.ExamDto;
import com.example.test_system.security.CurrentUser;
import com.example.test_system.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/exam")
public class ExamController {
    private final ExamService examService;

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping
    public HttpEntity<ApiResponse> saveExam(@RequestBody ExamDto examDto) {
        ApiResponse apiResponse = examService.saveExam(examDto);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping
    public HttpEntity<ApiResponse> getAllExams() {
        ApiResponse apiResponse = examService.getAllExams();
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/{id}")
    public HttpEntity<ApiResponse> getExamById(@PathVariable Integer id) {
        ApiResponse apiResponse = examService.getExamById(id);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }

    @PreAuthorize("hasRole('TEACHER')")
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

    @GetMapping("/groups/by-last-week")
    public HttpEntity<ApiResponse> getByLastWeek(){
        ApiResponse apiResponse = examService.getExamsByLastWeek();
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }

    @GetMapping("/student")
    public HttpEntity<ApiResponse> getExamsByStudent(UUID studentId){
        ApiResponse examsByStudentId = examService.getExamsByStudentId(studentId);
        return ResponseEntity.status(examsByStudentId.getHttpStatus()).body(examsByStudentId);
    }
}
