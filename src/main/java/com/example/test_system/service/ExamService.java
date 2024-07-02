package com.example.test_system.service;

import com.example.test_system.entity.Exam;
import com.example.test_system.entity.Group;
import com.example.test_system.entity.Test;
import com.example.test_system.entity.User;
import com.example.test_system.exceptions.GenericException;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.ExamDto;
import com.example.test_system.payload.TestDto;
import com.example.test_system.repository.ExamRepository;
import com.example.test_system.repository.GroupRepository;
import com.example.test_system.repository.TestRepository;
import com.example.test_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExamService {
    private final ExamRepository examRepository;
    private final TestRepository testRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public ApiResponse saveExam(ExamDto examDto) {
        Test test = testRepository.findById(examDto.getTestId())
                .orElseThrow(() -> GenericException.builder().message("Test not found").statusCode(404).build());
        Group group = groupRepository.findById(examDto.getGroupId())
                .orElseThrow(() -> GenericException.builder().message("Group not found").statusCode(404).build());
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
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> GenericException.builder().message("Exam not found").build());
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
        Group group = groupRepository.findById(examDto.getGroupId())
                .orElseThrow(() -> GenericException.builder().message("Group not found").build());
        Test test = testRepository.findById(examDto.getTestId())
                .orElseThrow(() -> GenericException.builder().message("Test not found").build());
        Exam exam = examRepository.findById(examDto.getId())
                .orElseThrow(() -> GenericException.builder().message("Exam not found").build());
        exam.setStartDate(examDto.getStartDate());
        exam.setFinishDate(examDto.getFinishDate());
        exam.setGroup(group);
        exam.setTest(test);
        examRepository.save(exam);
        return new ApiResponse("Success",true, HttpStatus.OK,null);

    }

    public ApiResponse deleteExamById(Integer id) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> GenericException.builder().message("Exam not found").build());
        examRepository.delete(exam);
        return new ApiResponse("Success",true, HttpStatus.OK,null);
    }


    public ApiResponse getExamsByStudentId(UUID studentId) {
        User user = userRepository.findById(studentId)
                .orElseThrow(() -> GenericException.builder().message("User not found").statusCode(404).build());
        List<Group> groups = user.getGroup();
        for (Group group : groups) {
            List<Exam> allByGroupId = examRepository.findAllByGroup_IdAndFinishDateAfter(group.getId(), LocalDate.now());
            return new ApiResponse("Success",true, HttpStatus.OK,allByGroupId);
        }
        return new ApiResponse("User not found",true, HttpStatus.NOT_FOUND,null);
    }
}
