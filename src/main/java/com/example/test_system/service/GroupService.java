package com.example.test_system.service;

import com.example.test_system.entity.Category;
import com.example.test_system.entity.Group;
import com.example.test_system.entity.User;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.GroupDto;
import com.example.test_system.repository.CategoryRepository;
import com.example.test_system.repository.GroupRepository;
import com.example.test_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public ApiResponse addGroup(GroupDto groupDto) {
        boolean existsed = groupRepository.existsByName(groupDto.getName());
        if (!existsed){
            Category category = categoryRepository.findById(groupDto.getCategoryId())
                    .orElseThrow(() -> new ResourceAccessException("Category not found"));
                User user1 = userRepository.findById(groupDto.getTeacherId())
                        .orElseThrow(() -> new ResourceAccessException("Teacher not found"));
                Group group= Group.builder()
                        .name(groupDto.getName())
                        .category(category)
                        .teacherId(user1)
                        .build();
                groupRepository.save(group);
                return new ApiResponse("Success",true, HttpStatus.OK,null);
        }
        return new ApiResponse("Group already exists",false,HttpStatus.BAD_REQUEST,null);
    }

    public ApiResponse getAllGroups() {
        List<Group> groups = groupRepository.findAll();
        List<GroupDto> groupDtos = new ArrayList<>();
        for (Group group : groups) {
            GroupDto groupDto= GroupDto.builder()
                    .name(group.getName())
                    .categoryId(group.getCategory().getId())
                    .teacherId(group.getTeacherId().getId())
                    .build();
            groupDtos.add(groupDto);
            return new ApiResponse("Success",true,HttpStatus.OK,groupDtos);
        }
        return new ApiResponse("Failed",false,HttpStatus.OK,groupDtos);
    }

    public ApiResponse getOneGroup(Integer id) {
        Group group = groupRepository.findById(id).orElseThrow(() -> new ResourceAccessException("Group not found"));
        GroupDto groupDto= GroupDto.builder()
                .name(group.getName())
                .categoryId(group.getCategory().getId())
                .teacherId(group.getTeacherId().getId())
                .build();
        return new ApiResponse("Success",true,HttpStatus.OK,groupDto);
    }

    public ApiResponse updateGroup(GroupDto groupDto) {
        Group group = groupRepository.findById(groupDto.getId()).orElseThrow(() -> new ResourceAccessException("Group not found"));
        boolean existsed = groupRepository.existsByName(groupDto.getName());
        if (!existsed){
            Category category = categoryRepository.findById(groupDto.getCategoryId()).orElseThrow(() -> new ResourceAccessException("Category not found"));
            User user = userRepository.findById(groupDto.getTeacherId()).orElseThrow(() -> new ResourceAccessException("Teacher not found"));
            group.setName(groupDto.getName());
            group.setCategory(category);
            group.setTeacherId(user);
            groupRepository.save(group);
            return new ApiResponse("Success",true,HttpStatus.OK,null);
        }
        return new ApiResponse("Group already exists",false,HttpStatus.BAD_REQUEST,null);
    }

    public ApiResponse deleteGroup(Integer id) {
        Group group = groupRepository.findById(id).orElseThrow(() -> new ResourceAccessException("Group not found"));
        groupRepository.delete(group);
        return new ApiResponse("Success",true,HttpStatus.OK,null);
    }
}
