package com.example.test_system.controller;

import com.example.test_system.entity.User;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.UserDto;
import com.example.test_system.security.CurrentUser;
import com.example.test_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping
    public HttpEntity<ApiResponse> saveUser(@RequestBody UserDto userDto, @CurrentUser User user){
        ApiResponse apiResponse = userService.saveUser(userDto, user);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }
}
