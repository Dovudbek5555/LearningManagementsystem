package com.example.test_system.service;

import com.example.test_system.entity.Address;
import com.example.test_system.entity.Group;
import com.example.test_system.entity.User;
import com.example.test_system.entity.enums.RoleEnum;
import com.example.test_system.exceptions.GenericException;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.UserDto;
import com.example.test_system.repository.AddressRepository;
import com.example.test_system.repository.GroupRepository;
import com.example.test_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;

    public ApiResponse saveStudent(UserDto userDto){
        Address address = addressRepository.findById(userDto.getAddressId())
                .orElseThrow(() -> GenericException.builder().message("Address not found").statusCode(400).build());
        boolean existsed = userRepository.existsByPhoneNumber(userDto.getPhoneNumber());
        if(!existsed){
            List<Group> groupList = new ArrayList<>();
            return saveUsers(userDto,address,groupList);
        }
        return new ApiResponse("Failed",false, HttpStatus.CONFLICT,null);
    }

    public ApiResponse getAllUsers(){
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            List<Integer> groupIds =new ArrayList<>();
            for (Group group : user.getGroup()) {
                groupIds.add(group.getId());
            }
            UserDto userDto =UserDto.builder()
                    .id(user.getId())
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .phoneNumber(user.getPhoneNumber())
                    .birthDate(user.getBirthDate())
                    .roleEnum(String.valueOf(user.getRoleEnum()))
                    .addressId(user.getAddress().getId())
                    .groupId(groupIds)
                    .build();
            userDtos.add(userDto);
        }
        return new ApiResponse("Success",true, HttpStatus.OK, userDtos);
    }

    public ApiResponse getOneUser(UUID id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> GenericException.builder().message("User not found").statusCode(400).build());
        List<Integer> groupIds =new ArrayList<>();
        for (Group group : user.getGroup()) {
            groupIds.add(group.getId());
        }
        UserDto userDto= UserDto.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .phoneNumber(user.getPhoneNumber())
                .birthDate(user.getBirthDate())
                .roleEnum(String.valueOf(user.getRoleEnum()))
                .addressId(user.getAddress().getId())
                .groupId(groupIds)
                .build();
        return new ApiResponse("Success",true, HttpStatus.OK,userDto);
    }

    public ApiResponse updateUser(UserDto userDto){
        List<Group> groupList = groupRepository.findAll();
        Address address = addressRepository.findById(userDto.getAddressId())
                .orElseThrow(() -> GenericException.builder().message("Address not found").statusCode(400).build());
        User user = userRepository.findById(userDto.getId())
                .orElseThrow(() -> GenericException.builder().message("User not found").statusCode(400).build());
            user.setFirstname(userDto.getFirstname());
            user.setLastname(userDto.getLastname());
            user.setPhoneNumber(userDto.getPhoneNumber());
            user.setBirthDate(userDto.getBirthDate());
            user.setAddress(address);
            user.setGroup(groupList);
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userRepository.save(user);
            return new ApiResponse("Success",true, HttpStatus.OK,null);
    }

    public ApiResponse deleteUser(UUID id){
        User user = userRepository.findById(id).orElseThrow(() -> GenericException.builder().message("User not found").statusCode(400).build());
        userRepository.delete(user);
            return new ApiResponse("Success",true, HttpStatus.OK,null);
    }

    public ApiResponse saveUsers(UserDto userDto,Address address,List<Group> group){
        User user = User.builder()
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .phoneNumber(userDto.getPhoneNumber())
                .birthDate(userDto.getBirthDate())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .address(address)
                .group(group)
                .roleEnum(RoleEnum.valueOf(userDto.getRoleEnum()))
                .build();
        userRepository.save(user);
        return new ApiResponse("User successfully saved",true, HttpStatus.OK,user);
    }

    public ApiResponse findStudentByLastWeek(RoleEnum roleEnum){
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusDays(6);
        Integer i = userRepository.countByCreatedDateIsAfterAndRoleEnum(startDate, roleEnum);
        return new ApiResponse("Students added last 6 days", true, HttpStatus.OK, i);
    }
}
