package com.example.test_system.service;

import com.example.test_system.entity.Exam;
import com.example.test_system.entity.Result;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.repository.ResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final ResultRepository resultRepository;

    public ApiResponse getRatingByExam(Integer examId){
        List<Result> result = resultRepository.findAllByExam_Id(examId);



    }

}
