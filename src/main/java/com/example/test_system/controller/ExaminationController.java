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

    @GetMapping("/start-test/{id}")
    public HttpEntity<ApiResponse> startExam(@PathVariable Integer id, @CurrentUser User user){
        ApiResponse apiResponse = examinationService.startTest(id, user);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }

    @PostMapping("/pass-test/{id}")
    public HttpEntity<ApiResponse> passResult(@PathVariable Integer id, @RequestBody List<AnswerDto> answerDtos){
        ApiResponse apiResponse = examinationService.passResult(id, answerDtos);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }

}
