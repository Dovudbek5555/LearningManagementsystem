package com.example.test_system.controller;

import com.example.test_system.entity.Answer;
import com.example.test_system.entity.User;
import com.example.test_system.payload.AnswerDto;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.ResultDto;
import com.example.test_system.security.CurrentUser;
import com.example.test_system.service.ExaminationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/examination")
public class ExaminationController {

    private final ExaminationService examinationService;

    @GetMapping("/start-test/{examId}")
    public HttpEntity<ApiResponse> startExam(@PathVariable Integer examId, @CurrentUser User user){
        ApiResponse apiResponse = examinationService.startTest(examId, user);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }

    @PostMapping("/pass-test/{resultId}")
    public HttpEntity<ApiResponse> passResult(@PathVariable Integer resultId, @RequestBody List<AnswerDto> answerDtos){
        ApiResponse apiResponse = examinationService.passResult(resultId, answerDtos);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }

}
