package com.example.test_system.service;

import com.example.test_system.entity.Category;
import com.example.test_system.entity.Group;
import com.example.test_system.entity.User;
import com.example.test_system.exceptions.GenericException;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.GroupDto;
import com.example.test_system.repository.CategoryRepository;
import com.example.test_system.repository.GroupRepository;
import com.example.test_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
                    .orElseThrow(() -> GenericException.builder().message("Category not found").build());
                User user1 = userRepository.findById(groupDto.getTeacherId())
                        .orElseThrow(() -> GenericException.builder().message("User not found").build());
                Group group= Group.builder()
                        .name(groupDto.getName())
                        .category(category)
                        .teacher(user1)
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
                    .teacherId(group.getTeacher().getId())
                    .build();
            groupDtos.add(groupDto);
        }
        return new ApiResponse("Success",true,HttpStatus.OK,groupDtos);
    }

    public ApiResponse getOneGroup(Integer id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> GenericException.builder().message("Group not found").build());
        GroupDto groupDto= GroupDto.builder()
                .name(group.getName())
                .categoryId(group.getCategory().getId())
                .teacherId(group.getTeacher().getId())
                .build();
        return new ApiResponse("Success",true,HttpStatus.OK,groupDto);
    }

    public ApiResponse updateGroup(GroupDto groupDto) {
        Group group = groupRepository.findById(groupDto.getId())
                .orElseThrow(() -> GenericException.builder().message("Group not found").build());
        boolean existsed = groupRepository.existsByName(groupDto.getName());
        if (!existsed){
            Category category = categoryRepository.findById(groupDto.getCategoryId())
                    .orElseThrow(() -> GenericException.builder().message("Category not found").build());
            User user = userRepository.findById(groupDto.getTeacherId())
                    .orElseThrow(() -> GenericException.builder().message("User not found").build());
            group.setName(groupDto.getName());
            group.setCategory(category);
            group.setTeacher(user);
            groupRepository.save(group);
            return new ApiResponse("Success",true,HttpStatus.OK,null);
        }
        return new ApiResponse("Group already exists",false,HttpStatus.BAD_REQUEST,null);
    }

    public ApiResponse deleteGroup(Integer id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> GenericException.builder().message("Group not found").build());
        groupRepository.delete(group);
        return new ApiResponse("Success",true,HttpStatus.OK,null);
    }

    public ApiResponse findGroupByTeacher(UUID teacherId){
        List<Group> allByTeacherId = groupRepository.findAllByTeacherId_Id(teacherId);
        return new ApiResponse("Success",true,HttpStatus.OK,allByTeacherId);
    }

    public ApiResponse findGroupByCategory(Integer categoryId){
        List<Group> allByCategoryId = groupRepository.findAllByCategory_Id(categoryId);
        return new ApiResponse("Success",true,HttpStatus.OK,allByCategoryId);
    }

    public ApiResponse findGroupByLastWeek(){
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusDays(6);
        Integer i = groupRepository.countByCreatedAtAfter(startDate);
        return new ApiResponse("Groups created in last 6 days", true, HttpStatus.OK, i);
    }
}
