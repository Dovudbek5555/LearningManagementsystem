package com.example.test_system.entity;

import com.example.test_system.entity.Enums.RoleEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

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

    @Column(nullable = false)
    private String firstname;

    private String lastname;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    private Date birthDate;

    @OneToOne
    private Address address;

    @Enumerated(EnumType.STRING)
    private RoleEnum roleEnum;

    @Column(nullable = false)
    private String password;

}
