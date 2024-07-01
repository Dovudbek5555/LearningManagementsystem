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
    public HttpEntity<ApiResponse> addSubCategory(@RequestBody SubCategoryDto subCategoryDto) {
        ApiResponse apiResponse = subCategoryService.saveSubCategory(subCategoryDto);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }
    @GetMapping
    public HttpEntity<ApiResponse> getSubCategoryList() {
        ApiResponse apiResponse = subCategoryService.getSubCategory();
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);

    }
    @GetMapping("/{id}")
    public HttpEntity<ApiResponse> getSubCategoryById(@PathVariable Integer id) {
        ApiResponse subCategory = subCategoryService.getOneSubCategory(id);
        return ResponseEntity.status(subCategory.getHttpStatus()).body(subCategory);
    }
    @PutMapping
    public HttpEntity<ApiResponse> updateSubCategory(@RequestBody SubCategoryDto subCategoryDto) {
        ApiResponse apiResponse = subCategoryService.updateSubCategory(subCategoryDto);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<ApiResponse> deleteSubCategory(@PathVariable Integer id) {
        ApiResponse apiResponse = subCategoryService.deleteSubCategory(id);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }

    @GetMapping("/byCategory")
    public HttpEntity<ApiResponse> getSubCategoryByCategory(@RequestParam Integer categoryId) {
        ApiResponse category = subCategoryService.findAllByCategory(categoryId);
        return ResponseEntity.status(category.getHttpStatus()).body(category);
    }
}


