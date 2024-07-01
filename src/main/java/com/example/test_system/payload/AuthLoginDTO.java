package com.example.test_system.payload;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthLoginDTO {
    private String phoneNumber;
    private String password;
}
