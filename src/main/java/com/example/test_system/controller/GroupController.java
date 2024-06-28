package com.example.test_system.controller;

import com.example.test_system.entity.User;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.GroupDto;
import com.example.test_system.security.CurrentUser;
import com.example.test_system.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    @PostMapping
    public HttpEntity<ApiResponse> saveGroup(@RequestBody GroupDto group, @CurrentUser User user) {
        ApiResponse apiResponse = groupService.addGroup(group, user);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }
}
