package com.example.test_system.payload;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Integer id;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private Date birthDate;
    private Integer addressId;
    private String roleEnum;
    private Integer groupId;
    private String password;
}
