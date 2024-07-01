package com.example.test_system.service;

import com.example.test_system.entity.Exam;
import com.example.test_system.entity.Result;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.RatingBySumCorrectCount;
import com.example.test_system.payload.ResultDto;
import com.example.test_system.repository.ExamRepository;
import com.example.test_system.repository.ResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final ResultRepository resultRepository;
    private final ExamRepository examRepository;

    public ApiResponse getTopStudentsByExam(Integer examId) {
        List<Result> results = resultRepository.findAllByExam_IdOrderByCorrectCountDesc(examId);
        if (results.isEmpty()) {
            return new ApiResponse("No results found for the specified exam", false, HttpStatus.NOT_FOUND, null);
        }
        List<ResultDto> resultDtos = new ArrayList<>();
        for (Result result : results) {
            ResultDto resultDto = ResultDto.builder().id(result.getId())
                    .studentId(result.getStudent().getId())
                    .examId(result.getExam().getId())
                    .correctCount(result.getCorrectCount())
                    .startTime(result.getStartTime())
                    .endTime(result.getEndTime())
                    .checked(result.getChecked())
                    .build();
            resultDtos.add(resultDto);
        }

        return new ApiResponse("Top students retrieved successfully", true, HttpStatus.OK, resultDtos);
    }

    public ApiResponse getTopStudentByDate(LocalDate startDate, LocalDate finishDate){
        List<Exam> examsBetweenDates = examRepository.findExamsBetweenDates(startDate, finishDate);
        List<RatingBySumCorrectCount> studentCorrectCountsByExams = resultRepository.findStudentCorrectCountsByExams(examsBetweenDates);
        return new ApiResponse("Top students retrieved successfully", true, HttpStatus.OK, studentCorrectCountsByExams);
    }






}
