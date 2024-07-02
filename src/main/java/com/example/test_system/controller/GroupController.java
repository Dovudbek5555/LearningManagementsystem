package com.example.test_system.controller;

import com.example.test_system.entity.User;
import com.example.test_system.entity.enums.RoleEnum;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.GroupDto;
import com.example.test_system.security.CurrentUser;
import com.example.test_system.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    @PostMapping
    public HttpEntity<ApiResponse> saveGroup(@RequestBody GroupDto group) {
//        user rolini priauthoritoryda tekshirish kerak
        ApiResponse apiResponse = groupService.addGroup(group);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }

    @PreAuthorize("hasRole('TEACHER')or('ADMIN')")
    @GetMapping
    public HttpEntity<ApiResponse> getAllGroups() {
        ApiResponse apiResponse = groupService.getAllGroups();
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }

    @PreAuthorize("hasRole('TEACHER')or('ADMIN')")
    @GetMapping("/{id}")
    public HttpEntity<ApiResponse> getGroupById(@PathVariable Integer id) {
        ApiResponse group = groupService.getOneGroup(id);
        return  ResponseEntity.status(group.getHttpStatus()).body(group);
    }

    @PreAuthorize("hasRole('TEACHER')or('ADMIN')")
    @PutMapping
    public HttpEntity<ApiResponse> updateGroup(@RequestBody GroupDto group) {
        ApiResponse apiResponse = groupService.updateGroup(group);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }

    @PreAuthorize("hasRole('TEACHER')or('ADMIN')")
    @DeleteMapping("/{id}")
    public HttpEntity<ApiResponse> deleteGroup(@PathVariable Integer id) {
        ApiResponse apiResponse = groupService.deleteGroup(id);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/byTeacher")
    public HttpEntity<ApiResponse> findByTeacher(@RequestParam UUID teacherId){
        ApiResponse groupByTeacher = groupService.findGroupByTeacher(teacherId);
        return ResponseEntity.status(groupByTeacher.getHttpStatus()).body(groupByTeacher);
    }

    @PreAuthorize("hasRole('TEACHER')or('ADMIN')")
    @GetMapping("/byCategory")
    public HttpEntity<ApiResponse> findByCategory(@RequestParam Integer categoryId){
        ApiResponse groupByCategory = groupService.findGroupByCategory(categoryId);
        return ResponseEntity.status(groupByCategory.getHttpStatus()).body(groupByCategory);
    }

    @PreAuthorize("hasRole('TEACHER')or('ADMIN')")
    @GetMapping("/groups/by-last-week")
    public HttpEntity<ApiResponse> getByLastWeek(){
        ApiResponse apiResponse = groupService.findGroupByLastWeek();
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }



}
