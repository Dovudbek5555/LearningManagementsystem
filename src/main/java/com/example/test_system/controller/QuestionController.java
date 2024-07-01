package com.example.test_system.controller;

import com.example.test_system.entity.User;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.QuestionDto;
import com.example.test_system.security.CurrentUser;
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
    @PutMapping
    public HttpEntity<ApiResponse> updateQuestion(@RequestBody QuestionDto questionDto) {
        ApiResponse apiResponse = questionService.updateQuestion(questionDto);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<ApiResponse> deleteQuestion(@PathVariable Integer id) {
        ApiResponse apiResponse = questionService.deleteQuestion(id);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }

    @GetMapping("/byDifficulty")
    public HttpEntity<ApiResponse> filterQuestionByDifficulty(@RequestParam String difficulty) {
        ApiResponse apiResponse = questionService.filterQuestionByDifficulty(difficulty);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }
    @GetMapping("/bySubCategory")
    public HttpEntity<ApiResponse> filterQuestionBySubCategory(@RequestParam Integer subCategoryId) {
        ApiResponse apiResponse = questionService.filterQuestionBySubCategory(subCategoryId);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }
}
