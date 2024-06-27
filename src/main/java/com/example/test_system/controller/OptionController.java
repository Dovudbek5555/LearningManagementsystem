package com.example.test_system.controller;

import com.example.test_system.entity.Option;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.OptionDto;
import com.example.test_system.repository.OptionRepository;
import com.example.test_system.service.OptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/option")
@RequiredArgsConstructor
public class OptionController {
    private final OptionService optionService;

    @PostMapping
    public HttpEntity<?> addOption(@RequestBody OptionDto optionDto) {
        ApiResponse apiResponse = optionService.saveOption(optionDto);
        return ResponseEntity.status(apiResponse.isSuccess()? HttpStatus.OK :HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}
