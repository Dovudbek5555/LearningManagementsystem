package com.example.test_system.service;

import com.example.test_system.entity.User;
import com.example.test_system.exceptions.UserNotFoundException;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.AuthLoginDTO;
import com.example.test_system.repository.UserRepository;
import com.example.test_system.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public ApiResponse login(AuthLoginDTO authLoginDTO) {
        User user = userRepository.findByPhoneNumber(authLoginDTO.getPhoneNumber())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if(authLoginDTO.getPassword().equals(user.getPassword())) {
            String token = jwtProvider.generateToken(authLoginDTO.getPhoneNumber());
            return new ApiResponse(token, true, HttpStatus.OK,null);
        }
        return new ApiResponse("password do not match", false,HttpStatus.UNAUTHORIZED,null);
    }

}
