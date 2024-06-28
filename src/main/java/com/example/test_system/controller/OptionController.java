package com.example.test_system.controller;

import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.OptionDto;
import com.example.test_system.service.OptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/option")
@RequiredArgsConstructor
public class OptionController {
    private final OptionService optionService;

    @PostMapping("/save")
    public HttpEntity<?> addOption(@RequestBody OptionDto optionDto) {
        ApiResponse apiResponse = optionService.saveOption(optionDto);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }
    @GetMapping("/list")
    public HttpEntity<ApiResponse> getOptions() {
        ApiResponse apiResponse = optionService.getOptionList();
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }
    @PutMapping("/update/{id}")
    public HttpEntity<ApiResponse> updateOption(@RequestBody OptionDto optionDto) {
        ApiResponse apiResponse = optionService.updateOption(optionDto);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }
    @DeleteMapping("/delete/{id}")
    public HttpEntity<ApiResponse> deleteOption(@PathVariable Integer id) {
        ApiResponse apiResponse = optionService.deleteOption(id);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }
}
