package com.example.test_system.service;

import com.example.test_system.entity.Address;
import com.example.test_system.entity.Group;
import com.example.test_system.entity.User;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.TestDto;
import com.example.test_system.payload.UserDto;
import com.example.test_system.repository.AddressRepository;
import com.example.test_system.repository.GroupRepository;
import com.example.test_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    private final GroupRepository groupRepository;
    public ApiResponse saveUser(UserDto userDto){
        boolean existsed = userRepository.existsByPhoneNumber(userDto.getPhoneNumber());
        if(!existsed){
            Address address = addressRepository.findById(userDto.getAddressId()).orElseThrow(() -> new ResourceAccessException("Address not found"));
            Group group = groupRepository.findById(userDto.getGroupId()).orElseThrow(() -> new ResourceAccessException("Group not found"));
            User user=User.builder()
                    .firstname(userDto.getFirstname())
                    .lastname(userDto.getLastname())
                    .phoneNumber(userDto.getPhoneNumber())
                    .address(address)
                    .group(group)
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .build();
            userRepository.save(user);
            return new ApiResponse("Success",true, HttpStatus.OK,null);
        }
        return new ApiResponse("Failed",false, HttpStatus.CONFLICT,null);
    }

//    public ApiResponse getUserList(UserDto userDto){}
}
