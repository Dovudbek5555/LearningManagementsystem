package com.example.test_system.controller;

import com.example.test_system.entity.User;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.TestDto;
import com.example.test_system.security.CurrentUser;
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
    public HttpEntity<ApiResponse> saveTest(@RequestBody TestDto testDto) {
        ApiResponse apiResponse = testService.saveTest(testDto);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }
    @GetMapping("/{id}")
    public HttpEntity<ApiResponse> getTests(@PathVariable Integer id){
        ApiResponse oneTest = testService.getOneTest(id);
        return ResponseEntity.status(oneTest.getHttpStatus()).body(oneTest);
    }
    @GetMapping
    public HttpEntity<ApiResponse> getTests(){
        ApiResponse allTests = testService.getAllTests();
        return ResponseEntity.status(allTests.getHttpStatus()).body(allTests);
    }
    @PutMapping
    public HttpEntity<ApiResponse> updateTest(@RequestBody TestDto testDto){
        ApiResponse apiResponse = testService.updateTest(testDto);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<ApiResponse> deleteTest(@PathVariable Integer id){
        ApiResponse apiResponse = testService.deleteTest(id);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }

    @GetMapping("/byTeacher")
    public HttpEntity<ApiResponse> getTestByTeacher(@CurrentUser User user){
        ApiResponse apiResponse = testService.getTestByTeacher(user);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }
}