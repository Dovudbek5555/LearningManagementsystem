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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final AddressRepository addressRepository;

    public ApiResponse saveUser(UserDto userDto, User user){
        boolean existsed = userRepository.existsByPhoneNumber(userDto.getPhoneNumber());
        if(!existsed){
            Group group = groupRepository.findById(userDto.getGroupId())
                    .orElseThrow(() -> GenericException.builder().message("Group not found").statusCode(400).build());
            Address address = addressRepository.findById(userDto.getAddressId())
                    .orElseThrow(() -> GenericException.builder().message("Address not found").statusCode(400).build());
            if (user.getRoleEnum()==RoleEnum.TEACHER) {
                User user1 = User.builder()
                        .firstname(userDto.getFirstname())
                        .lastname(userDto.getLastname())
                        .phoneNumber(userDto.getPhoneNumber())
                        .address(address)
                        .group(group)
                        .birthDate(userDto.getBirthDate())
                        .roleEnum(RoleEnum.STUDENT)
                        .build();
                userRepository.save(user1);
                return new ApiResponse("Success",true, HttpStatus.OK,null);
            } else if (user.getRoleEnum() == RoleEnum.ADMIN) {
                User user2 = User.builder()
                        .firstname(userDto.getFirstname())
                        .lastname(userDto.getLastname())
                        .phoneNumber(userDto.getPhoneNumber())
                        .address(address)
                        .group(group)
                        .birthDate(userDto.getBirthDate())
                        .roleEnum(RoleEnum.valueOf(userDto.getRoleEnum().toUpperCase()))
                        .build();
                userRepository.save(user2);
                return new ApiResponse("Success",true, HttpStatus.OK,null);
            }
        }
        return new ApiResponse("Failed",false, HttpStatus.CONFLICT,null);
    }
}
