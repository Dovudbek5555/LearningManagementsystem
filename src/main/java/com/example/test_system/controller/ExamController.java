package com.example.test_system.controller;

import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.ExamDto;
import com.example.test_system.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exam")
public class ExamController {
    private final ExamService examService;
    @PostMapping
    public HttpEntity<ApiResponse> saveExam(@RequestBody ExamDto examDto) {
        return null;
    }
}
