package com.example.test_system.Entity;

import com.example.test_system.Entity.Enums.RoleEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstname;

    private String lastname;

    private String phoneNumber;

    private Date birthDate;

    @OneToOne
    private Address address;

    @Enumerated(EnumType.STRING)
    private RoleEnum roleEnum;

    private String password;

    @ManyToOne
    private Group group;
}