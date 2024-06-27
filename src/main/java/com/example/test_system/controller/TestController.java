package com.example.test_system.controller;

import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.TestDto;
import com.example.test_system.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/{id}")
    public HttpEntity<?> getTests(@PathVariable Integer id){
        return null;
    }
}
