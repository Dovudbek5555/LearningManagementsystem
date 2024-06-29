package com.example.test_system.service;

import com.example.test_system.entity.Category;
import com.example.test_system.entity.SubCategory;
import com.example.test_system.exceptions.GenericException;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.SubCategoryDto;
import com.example.test_system.payload.TestDto;
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
            Category category = categoryRepository.findById(subCategoryDto.getCategoryId())
                    .orElseThrow(() -> GenericException.builder()
                            .message("Category not found").statusCode(400).build());
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
                    .id(subCategory.getId())
                    .name(subCategory.getName())
                    .categoryId(subCategory.getCategory().getId())
                    .build();
            subCategoryDtos.add(subCategoryDto1);
        }
        return new ApiResponse("Success", true, HttpStatus.OK, subCategoryDtos);
    }

    public ApiResponse getOneSubCategory(Integer id) {
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> GenericException.builder()
                        .message("SubCategory not found").statusCode(400).build());
        SubCategoryDto subCategoryDto= SubCategoryDto.builder()
                .id(subCategory.getId())
                .name(subCategory.getName())
                .categoryId(subCategory.getCategory().getId())
                .build();
        return new ApiResponse("Success",true,HttpStatus.OK,subCategoryDto);
    }

    public ApiResponse updateSubCategory(SubCategoryDto subCategoryDto) {
        boolean exists = subCategoryRepository.existsByName(subCategoryDto.getName());
        if (!exists) {
            Category category = categoryRepository.findById(subCategoryDto.getCategoryId())
                    .orElseThrow(() -> GenericException.builder()
                            .message("Category not found").statusCode(400).build());
            SubCategory subCategory = subCategoryRepository.findById(subCategoryDto.getId())
                    .orElseThrow(() -> GenericException.builder()
                            .message("SubCategory not found").statusCode(400).build());
            subCategory.setId(subCategoryDto.getId());
            subCategory.setName(subCategoryDto.getName());
            subCategory.setCategory(category);
            subCategoryRepository.save(subCategory);
            return new ApiResponse("Success", true, HttpStatus.OK, null);
        }
        return new ApiResponse("Error", false, HttpStatus.CONFLICT, null);
    }

    public ApiResponse deleteSubCategory(Integer id){
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> GenericException.builder()
                        .message("SubCategory not found").statusCode(400).build());
        subCategoryRepository.delete(subCategory);
        return new ApiResponse("Success", true, HttpStatus.OK, null);
    }
}