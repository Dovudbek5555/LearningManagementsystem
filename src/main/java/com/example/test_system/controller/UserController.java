package com.example.test_system.controller;

import com.example.test_system.entity.User;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.UserDto;
import com.example.test_system.security.CurrentUser;
import com.example.test_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping
    public HttpEntity<ApiResponse> saveStudent(@RequestBody UserDto userDto){
        ApiResponse apiResponse = userService.saveStudent(userDto);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }
    @GetMapping
    public HttpEntity<ApiResponse> getUserList(){
        ApiResponse allUsers = userService.getAllUsers();
        return ResponseEntity.status(allUsers.getHttpStatus()).body(allUsers);
    }
    @GetMapping("/{id}")
    public HttpEntity<ApiResponse> getUserById(@PathVariable UUID id){
        ApiResponse user = userService.getOneUser(id);
        return ResponseEntity.status(user.getHttpStatus()).body(user);
    }

    @PutMapping
    public HttpEntity<ApiResponse> updateUser(@RequestBody UserDto userDto){
        ApiResponse apiResponse = userService.updateUser(userDto);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<ApiResponse> deleteUser(@PathVariable UUID id){
        ApiResponse apiResponse = userService.deleteUser(id);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }
}
