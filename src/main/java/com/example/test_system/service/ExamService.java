package com.example.test_system.service;

import com.example.test_system.entity.Exam;
import com.example.test_system.entity.Group;
import com.example.test_system.entity.Test;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.ExamDto;
import com.example.test_system.payload.TestDto;
import com.example.test_system.repository.ExamRepository;
import com.example.test_system.repository.GroupRepository;
import com.example.test_system.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamService {
    private final ExamRepository examRepository;
    private final TestRepository testRepository;
    private final GroupRepository groupRepository;
    public ApiResponse saveExam(ExamDto examDto) {
        Test test = testRepository.findById(examDto.getTestId()).orElseThrow(() -> new ResourceAccessException("Test not found"));
        Group group = groupRepository.findById(examDto.getGroupId()).orElseThrow(() -> new ResourceAccessException("Group not found"));
        Exam exam = Exam.builder()
                .test(test)
                .group(group)
                .startDate(examDto.getStartDate())
                .finishDate(examDto.getFinishDate())
                .build();
        examRepository.save(exam);
        return new ApiResponse("Success",true, HttpStatus.OK,null);
    }

    public ApiResponse getAllExams() {
        List<Exam> exams = examRepository.findAll();
        List<ExamDto> examDtos=new ArrayList<>();
        for (Exam exam : exams) {
            ExamDto examDto= ExamDto.builder()
                    .id(exam.getId())
                    .startDate(exam.getStartDate())
                    .finishDate(exam.getFinishDate())
                    .groupId(exam.getGroup().getId())
                    .testId(exam.getTest().getId())
                    .build();
            examDtos.add(examDto);
        }
        return new ApiResponse("Success",true, HttpStatus.OK,examDtos);
    }

    public ApiResponse getExamById(Integer id) {
        Exam exam = examRepository.findById(id).orElseThrow(() -> new ResourceAccessException("Exam not found"));
        ExamDto examDto= ExamDto.builder()
                .id(exam.getId())
                .startDate(exam.getStartDate())
                .finishDate(exam.getFinishDate())
                .groupId(exam.getGroup().getId())
                .testId(exam.getTest().getId())
                .build();
        return new ApiResponse("Success",true, HttpStatus.OK,examDto);
    }

    public ApiResponse updateExam(ExamDto examDto) {
        Group group = groupRepository.findById(examDto.getGroupId()).orElseThrow(() -> new ResourceAccessException("Group not found"));
        Test test = testRepository.findById(examDto.getTestId()).orElseThrow(() -> new ResourceAccessException("Test not found"));
        Exam exam = examRepository.findById(examDto.getId()).orElseThrow(() -> new ResourceAccessException("Exam not found"));
        exam.setStartDate(examDto.getStartDate());
        exam.setFinishDate(examDto.getFinishDate());
        exam.setGroup(group);
        exam.setTest(test);
        examRepository.save(exam);
        return new ApiResponse("Success",true, HttpStatus.OK,null);

    }

    public ApiResponse deleteExamById(Integer id) {
        Exam exam = examRepository.findById(id).orElseThrow(() -> new ResourceAccessException("Exam not found"));
        examRepository.delete(exam);
        return new ApiResponse("Success",true, HttpStatus.OK,null);
    }
}
