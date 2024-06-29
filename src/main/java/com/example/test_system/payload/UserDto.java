package com.example.test_system.payload;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private UUID id;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private Date birthDate;
    private Integer addressId;
    private Integer groupId;
    private String roleEnum;
    private String password;
}
