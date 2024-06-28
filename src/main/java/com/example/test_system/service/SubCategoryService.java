package com.example.test_system.service;

import com.example.test_system.entity.Category;
import com.example.test_system.entity.SubCategory;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.SubCategoryDto;
import com.example.test_system.repository.CategoryRepository;
import com.example.test_system.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;

    public ApiResponse saveSubCategory(SubCategoryDto subCategoryDto) {
        boolean exists = subCategoryRepository.existsByName(subCategoryDto.getName());
        if (!exists) {
            Category category = categoryRepository.findById(subCategoryDto.getCategoryId()).orElseThrow(() -> new ResourceAccessException("Category not found"));
            SubCategory subCategory1 = SubCategory.builder()
                    .name(subCategoryDto.getName())
                    .category(category)
                    .build();
            subCategoryRepository.save(subCategory1);
            return new ApiResponse("Success", true, HttpStatus.OK, null);
        }
        return new ApiResponse("Error", false, HttpStatus.CONFLICT, null);
    }

    public ApiResponse getSubCategory() {
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        List<SubCategoryDto> subCategoryDtos=new ArrayList<>();
        for (SubCategory subCategory : subCategoryList) {
            SubCategoryDto subCategoryDto1= SubCategoryDto.builder()
                    .name(subCategory.getName())
                    .categoryId(subCategory.getCategory().getId())
                    .build();
            subCategoryDtos.add(subCategoryDto1);
        }
        return new ApiResponse("Success", true, HttpStatus.OK, subCategoryDtos);
    }

}