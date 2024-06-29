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

    public ApiResponse saveUser(UserDto userDto, User user){
        boolean existsed = userRepository.existsByPhoneNumber(userDto.getPhoneNumber());
        if(!existsed){
            Group group = groupRepository.findById(userDto.getGroupId())
                    .orElseThrow(() -> GenericException.builder().message("Group not found").statusCode(400).build());
            Address address = addressRepository.findById(userDto.getAddressId())
                    .orElseThrow(() -> GenericException.builder().message("Address not found").statusCode(400).build());
            if (user.getRoleEnum()==RoleEnum.TEACHER) {
                saveUser(userDto, address, group);
            } else if (user.getRoleEnum() == RoleEnum.ADMIN) {
                User user3 = User.builder()
                        .firstname(userDto.getFirstname())
                        .lastname(userDto.getLastname())
                        .phoneNumber(userDto.getPhoneNumber())
                        .address(address)
                        .groups(List.of(group))
                        .birthDate(userDto.getBirthDate())
                        .roleEnum(RoleEnum.STUDENT)
                        .password(passwordEncoder.encode(userDto.getPassword()))
                        .build();
                userRepository.save(user3);
            }
        }
        return new ApiResponse("Shunday telefon raqamli user mavjud",false, HttpStatus.CONFLICT,null);
    }

    public ApiResponse getAllUsers(){
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            for (Group group : user.getGroups()) {
                UserDto userDto =UserDto.builder()
                        .firstname(user.getFirstname())
                        .lastname(user.getLastname())
                        .phoneNumber(user.getPhoneNumber())
                        .birthDate(user.getBirthDate())
                        .roleEnum(String.valueOf(user.getRoleEnum()))
                        .addressId(user.getAddress().getId())
                        .groupId(group.getId())
                        .build();
                userDtos.add(userDto);
            }

        }
        return new ApiResponse("Success",true, HttpStatus.OK,userDtos);
    }

    public ApiResponse getOneUser(UUID id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> GenericException.builder().message("User not found").statusCode(400).build());
        for (Group group : user.getGroups()) {
            UserDto userDto= UserDto.builder()
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .phoneNumber(user.getPhoneNumber())
                    .birthDate(user.getBirthDate())
                    .roleEnum(String.valueOf(user.getRoleEnum()))
                    .addressId(user.getAddress().getId())
                    .groupId(group.getId())
                    .build();
            return new ApiResponse("Success",true, HttpStatus.OK,userDto);
        }
        return new ApiResponse("User topilmadi",false,HttpStatus.NOT_FOUND,null);
    }

    public ApiResponse updateUser(UserDto userDto){
        Group group = groupRepository.findById(userDto.getGroupId())
                .orElseThrow(() -> GenericException.builder().message("Group not found").statusCode(400).build());
        Address address = addressRepository.findById(userDto.getAddressId())
                .orElseThrow(() -> GenericException.builder().message("Address not found").statusCode(400).build());
        User user = userRepository.findById(userDto.getId())
                .orElseThrow(() -> GenericException.builder().message("User not found").statusCode(400).build());
            user.setFirstname(userDto.getFirstname());
            user.setLastname(userDto.getLastname());
            user.setPhoneNumber(userDto.getPhoneNumber());
            user.setBirthDate(userDto.getBirthDate());
            user.setAddress(address);
            user.setGroups(List.of(group));
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userRepository.save(user);
            return new ApiResponse("Success",true, HttpStatus.OK,null);
    }

    public ApiResponse deleteUser(UUID id){
        User user = userRepository.findById(id).orElseThrow(() -> GenericException.builder().message("User not found").statusCode(400).build());
        userRepository.delete(user);
            return new ApiResponse("Success",true, HttpStatus.OK,null);
    }

    public ApiResponse saveUser(UserDto userDto,Address address, Group group){
        User user = User.builder()
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .phoneNumber(userDto.getPhoneNumber())
                .address(address)
                .groups(List.of(group))
                .birthDate(userDto.getBirthDate())
                .roleEnum(RoleEnum.STUDENT)
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();
        userRepository.save(user);
        return new ApiResponse("User succesfully saved",true, HttpStatus.OK,null);
    }
}
