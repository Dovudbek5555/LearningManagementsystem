package com.example.test_system.controller;

import com.example.test_system.entity.User;
import com.example.test_system.entity.enums.RoleEnum;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.UserDto;
import com.example.test_system.security.CurrentUser;
import com.example.test_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public HttpEntity<ApiResponse> saveStudent(@RequestBody UserDto userDto){
        ApiResponse apiResponse = userService.saveStudent(userDto);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }
    @GetMapping
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public HttpEntity<ApiResponse> getUserList(){
        ApiResponse allUsers = userService.getAllUsers();
        return ResponseEntity.status(allUsers.getHttpStatus()).body(allUsers);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public HttpEntity<ApiResponse> getUserById(@PathVariable UUID id){
        ApiResponse user = userService.getOneUser(id);
        return ResponseEntity.status(user.getHttpStatus()).body(user);
    }

    @PutMapping
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public HttpEntity<ApiResponse> updateUser(@RequestBody UserDto userDto){
        ApiResponse apiResponse = userService.updateUser(userDto);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public HttpEntity<ApiResponse> deleteUser(@PathVariable UUID id){
        ApiResponse apiResponse = userService.deleteUser(id);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }

    @PostMapping("/teacher")
    @PreAuthorize("hasRole('ADMIN')")
    public HttpEntity<ApiResponse> saveTeacher(@RequestBody UserDto userDto){
        ApiResponse apiResponse = userService.saveTeachers(userDto);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    @GetMapping("/by-last-week")
    public HttpEntity<ApiResponse> getStudentsByLastWeek(@RequestBody RoleEnum roleEnum){
        ApiResponse apiResponse = userService.findStudentByLastWeek(roleEnum);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }

    @GetMapping("/byRoleEnum")
    @PreAuthorize("hasRole('ADMIN')")
    public HttpEntity<ApiResponse> getUserByRoleEnum(@RequestParam RoleEnum roleEnum){
        ApiResponse allUserByRoleEnum = userService.findAllUserByRoleEnum(roleEnum);
        return ResponseEntity.status(allUserByRoleEnum.getHttpStatus()).body(allUserByRoleEnum);
    }
}
