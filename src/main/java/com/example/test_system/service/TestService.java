package com.example.test_system.service;

import com.example.test_system.entity.Group;
import com.example.test_system.entity.QuestionList;
import com.example.test_system.entity.SubCategory;
import com.example.test_system.entity.Test;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.TestDto;
import com.example.test_system.repository.GroupRepository;
import com.example.test_system.repository.QuestionListRepository;
import com.example.test_system.repository.SubCategoryRepository;
import com.example.test_system.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.time.Duration;

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
                .startActiveDate(testDto.getStartActiveDate())
                .finishActiveDate(testDto.getFinishActiveDate())
                .duration(duration)
                .passingScore(testDto.getPassingScore())
                .group(group)
                .subCategory(subCategory)
                .build();
        testRepository.save(test);
        return new ApiResponse("Test successfully saved",true);
    }
}
