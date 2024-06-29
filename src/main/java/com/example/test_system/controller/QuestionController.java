package com.example.test_system.controller;

import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.QuestionDto;
import com.example.test_system.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping
    public HttpEntity<ApiResponse> saveQuestion(@RequestBody QuestionDto questionDto) {
        ApiResponse apiResponse = questionService.saveQuestion(questionDto);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }

    @GetMapping
    public HttpEntity<ApiResponse> getAllQuestions() {
        ApiResponse questionList = questionService.getQuestionList();
        return ResponseEntity.status(questionList.getHttpStatus()).body(questionList);
    }
}
