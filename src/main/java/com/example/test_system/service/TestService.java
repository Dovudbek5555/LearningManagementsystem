package com.example.test_system.service;

import com.example.test_system.entity.*;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.TestDto;
import com.example.test_system.repository.GroupRepository;
import com.example.test_system.repository.QuestionListRepository;
import com.example.test_system.repository.SubCategoryRepository;
import com.example.test_system.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {
    private final TestRepository testRepository;
    private final QuestionListRepository questionListRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final GroupRepository groupRepository;

    public ApiResponse saveTest(TestDto testDto) {
        QuestionList questionList = questionListRepository.findById(testDto.getQuestionListId())
                .orElseThrow(() -> new ResourceAccessException("QuestionList not found"));
        SubCategory subCategory = subCategoryRepository.findById(testDto.getSubCategoryId())
                .orElseThrow(() -> new ResourceAccessException("SubCategory not found"));
        Group group = groupRepository.findById(testDto.getGroupId())
                .orElseThrow(() -> new ResourceAccessException("Group not found"));
        Duration duration=Duration.ofMinutes(testDto.getDuration());
        Test test=Test.builder()
                .questionList(questionList)
                .createdAt(LocalDate.now())
                .duration(duration)
                .passingScore(testDto.getPassingScore())
                .group(group)
                .subCategory(subCategory)
                .build();
        testRepository.save(test);
        return new ApiResponse("Test successfully saved",true, HttpStatus.OK,null);
    }

    public ApiResponse getOneTest(Integer id) {
        List<Question> oQuestions=new ArrayList<>();
        List<String> yQuestions=new ArrayList<>();
        Test test = testRepository.findById(id).orElseThrow(() -> new ResourceAccessException("Test not found"));
        for (Question oQuestion : test.getQuestionList().getOQuestions()) {

            oQuestions.add(oQuestion);
        }
        for (Y_Question yQuestion : test.getQuestionList().getYQuestions()) {
            yQuestions.add(yQuestion.getQuestion());
        }
        TestDto testDto=TestDto.builder()
                .createdAt(test.getCreatedAt())
                .passingScore(test.getPassingScore())
                .duration((int) test.getDuration().toMinutes())
                .oQuestion(oQuestions)
                .yQuestion(yQuestions)
                .build();
        return new ApiResponse("Success",true,HttpStatus.OK,testDto);
    }
    public ApiResponse getAllTests(){
        List<Test> testList = testRepository.findAll();
        List<TestDto> testDtoList=new ArrayList<>();
        for (Test test : testList) {
            TestDto testDto = TestDto.builder()
                    .groupId(test.getGroup().getId())
                    .createdAt(test.getCreatedAt())
                    .passingScore(test.getPassingScore())
                    .questionListId(test.getQuestionList().getId())
                    .duration((int) test.getDuration().toMinutes())
                    .build();
            testDtoList.add(testDto);
        }
        return new ApiResponse("Success",true,HttpStatus.OK,testDtoList);
    }

//    public ApiResponse uodateTest(TestDto testDto){
//
//    }
}
