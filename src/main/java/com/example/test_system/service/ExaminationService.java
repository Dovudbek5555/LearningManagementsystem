package com.example.test_system.service;

import com.example.test_system.entity.*;
import com.example.test_system.exceptions.GenericException;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.ResultDto;
import com.example.test_system.repository.ExamRepository;
import com.example.test_system.repository.GroupRepository;
import com.example.test_system.repository.ResultRepository;
import com.example.test_system.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExaminationService {

    private final ExamRepository examRepository;
    private final TestRepository testRepository;
    private final ResultRepository resultRepository;

    public ApiResponse startTest(Integer id, User user){
        Exam exam = examRepository.findById(id).orElseThrow(() -> GenericException.builder()
                .message("Exam not found").statusCode(404).build());
        Test test = testRepository.findById(exam.getId()).orElseThrow(() -> GenericException.builder()
                .message("Test not found").statusCode(404).build());
        if (exam.getGroup().getId()==user.getGroup().getId()){
            if (isExamAvailable(exam.getId())){
                Result result = Result.builder()
                        .student(user)
                        .test(test)
                        .startTime(LocalTime.now())
                        .endTime(LocalTime.now().plus(test.getDuration()))
                        .checked(false)
                        .build();
                resultRepository.save(result);
                ResultDto resultDto = ResultDto.builder()
                        .studentId(user.getId())
                        .testId(test.getId())
                        .startTime(LocalTime.now())
                        .endTime(LocalTime.now().plus(test.getDuration()))
                        .checked(false)
                        .build();
                return new ApiResponse
                ("Succesfully started exam", false, HttpStatus.OK, resultDto);
            }
            return new ApiResponse
            ("This exam is outdated", false, HttpStatus.METHOD_NOT_ALLOWED, null);
        }
        return new ApiResponse
        ("Your group not allowed for this test", false, HttpStatus.METHOD_NOT_ALLOWED, null);
    }


    public boolean isExamAvailable(Integer examId) {
        return examRepository.findById(examId)
                .map(exam -> exam.getFinishDate().isAfter(LocalDate.now()))
                .orElse(false);
    }

}
