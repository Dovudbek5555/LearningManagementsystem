package com.example.test_system.controller;

import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.TestDto;
import com.example.test_system.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
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
        return ResponseEntity.status(apiResponse.isSuccess()? HttpStatus.OK :HttpStatus.BAD_REQUEST).body(apiResponse);
    }
    @GetMapping("/{id}")
    public HttpEntity<?> getTests(@PathVariable Integer id){
        ApiResponse oneTest = testService.getOneTest(id);
        return ResponseEntity.status(oneTest.isSuccess()? HttpStatus.OK :HttpStatus.BAD_REQUEST).body(oneTest);
    }
    @GetMapping
    public HttpEntity<?> getTests(){
        ApiResponse allTests = testService.getAllTests();
        return ResponseEntity.status(allTests.isSuccess()? HttpStatus.OK :HttpStatus.BAD_REQUEST).body(allTests);
    }
}
