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

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    public ApiResponse addGroup(GroupDto groupDto, User user) {
        boolean existsed = groupRepository.existsByName(groupDto.getName());
        if (!existsed){
            Category category = categoryRepository.findById(groupDto.getCategoryId()).orElseThrow(() -> new ResourceAccessException("Category not found"));
            if (user.getRoleEnum().toString().equals("TEACHER")) {
                User user1 = userRepository.findById(groupDto.getTeacherId()).orElseThrow(() -> new ResourceAccessException("Teacher not found"));
                Group group= Group.builder()
                        .name(groupDto.getName())
                        .category(category)
                        .teacherId(user1)
                        .build();
                groupRepository.save(group);
                return new ApiResponse("Success",true, HttpStatus.OK,null);
            }
            return new ApiResponse("Role Teacher invalid",false,HttpStatus.BAD_REQUEST,null);
        }
        return new ApiResponse("Group already exists",false,HttpStatus.BAD_REQUEST,null);
    }
}
