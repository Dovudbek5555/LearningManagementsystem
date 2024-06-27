package com.example.test_system.controller;

import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.TestDto;
import com.example.test_system.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/start-test")
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;

    @PostMapping
    public HttpEntity<?> saveTest(@RequestBody TestDto testDto) {
        ApiResponse apiResponse = testService.saveTest(testDto);
        return ResponseEntity.status(apiResponse.isSuccess()? 200:400).body(apiResponse);
    }
}
