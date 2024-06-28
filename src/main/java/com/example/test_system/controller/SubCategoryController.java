package com.example.test_system.controller;

import com.example.test_system.entity.SubCategory;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.SubCategoryDto;
import com.example.test_system.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subCategory")
@RequiredArgsConstructor
public class SubCategoryController {
    private final SubCategoryService subCategoryService;
    @PostMapping
    public HttpEntity<?> addSubCategory(@RequestBody SubCategoryDto subCategoryDto) {
        ApiResponse apiResponse = subCategoryService.saveSubCategory(subCategoryDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(apiResponse);
    }
    @GetMapping
    public HttpEntity<?> getSubCategoryList() {
        ApiResponse apiResponse = subCategoryService.getSubCategory();
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}


