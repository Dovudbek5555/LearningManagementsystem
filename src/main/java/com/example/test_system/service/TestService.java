package com.example.test_system.service;

import com.example.test_system.entity.*;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.TestDto;
import com.example.test_system.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {
    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final GroupRepository groupRepository;

    public ApiResponse saveTest(TestDto testDto) {
        Question questionList =questionRepository.findById(testDto.getQuestionId())
                .orElseThrow(() -> new ResourceAccessException("Question not found"));
        SubCategory subCategory = subCategoryRepository.findById(testDto.getSubCategoryId())
                .orElseThrow(() -> new ResourceAccessException("SubCategory not found"));
        Duration duration=Duration.ofMinutes(testDto.getDuration());
        Test test=Test.builder()
                .questionList(List.of(questionList))
                .createdAt(LocalDate.now())
                .duration(duration)
                .passingScore(testDto.getPassingScore())
                .subCategory(subCategory)
                .build();
        testRepository.save(test);
        return new ApiResponse("Test successfully saved",true, HttpStatus.OK,null);
    }

    public ApiResponse getOneTest(Integer id) {
        List<Question> oQuestions=new ArrayList<>();
        Test test = testRepository.findById(id).orElseThrow(() -> new ResourceAccessException("Test not found"));
        for (Question question : test.getQuestionList()) {
            oQuestions.add(question);
        }
        TestDto testDto=TestDto.builder()
                .createdAt(test.getCreatedAt())
                .passingScore(test.getPassingScore())
                .duration((int) test.getDuration().toMinutes())
                .oQuestion(oQuestions)
                .build();
        return new ApiResponse("Success",true,HttpStatus.OK,testDto);
    }
    public ApiResponse getAllTests(){
        List<Test> testList = testRepository.findAll();
        List<TestDto> testDtoList=new ArrayList<>();
        for (Test test : testList) {
            for (Question question : test.getQuestionList()) {
                TestDto testDto = TestDto.builder()
                        .groupId(test.getGroup().getId())
                        .createdAt(test.getCreatedAt())
                        .passingScore(test.getPassingScore())
                        .duration((int) test.getDuration().toMinutes())
                        .questionId(question.getId())
                        .build();
                testDtoList.add(testDto);
            }

        }
        return new ApiResponse("Success",true,HttpStatus.OK,testDtoList);
    }

    public ApiResponse updateTest(TestDto testDto){
        SubCategory subCategory = subCategoryRepository.findById(testDto.getSubCategoryId())
                .orElseThrow(() -> new ResourceAccessException("SubCategory not found"));
        Question questionList =questionRepository.findById(testDto.getQuestionId())
                .orElseThrow(() -> new ResourceAccessException("Question not found"));
        Test test = testRepository.findById(testDto.getId())
                .orElseThrow(() -> new ResourceAccessException("Test not found"));
        test.setId(testDto.getId());
        test.setPassingScore(testDto.getPassingScore());
        test.setDuration(Duration.ofMinutes(testDto.getDuration()));
        test.setSubCategory(subCategory);
        test.setQuestionList(List.of(questionList));
        testRepository.save(test);
        return new ApiResponse("Success",true,HttpStatus.OK,test);
    }

    public ApiResponse deleteTest(Integer id){
        Test test = testRepository.findById(id).orElseThrow(() -> new ResourceAccessException("Test not found"));
        testRepository.delete(test);
        return new ApiResponse("Success",true,HttpStatus.OK,null);
    }
}
