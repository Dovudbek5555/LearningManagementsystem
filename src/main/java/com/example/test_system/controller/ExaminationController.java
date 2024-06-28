package com.example.test_system.controller;

import com.example.test_system.entity.User;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.security.CurrentUser;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/examination")
public class ExaminationController {

    @GetMapping("/start-test/{id}")
    public ApiResponse startExam(@PathVariable Integer id, @CurrentUser User user){

    }

}
