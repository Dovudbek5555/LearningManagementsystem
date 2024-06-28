package com.example.test_system.service;

import com.example.test_system.entity.Exam;
import com.example.test_system.entity.Group;
import com.example.test_system.entity.User;
import com.example.test_system.exceptions.GenericException;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.repository.ExamRepository;
import com.example.test_system.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExaminationService {

    private final ExamRepository examRepository;
    private final GroupRepository groupRepository;

    public ApiResponse startTest(Integer id, User user){
        groupRepository.findById(user.getGroup().getId()).orElseThrow(() -> GenericException.builder().message("You dont have a group").statusCode(404).build());



    }

}
