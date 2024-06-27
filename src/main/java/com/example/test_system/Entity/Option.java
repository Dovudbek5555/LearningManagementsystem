package com.example.test_system.Entity;

import com.example.test_system.Entity.Enums.OptionEnum;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private OptionEnum optionEnum;

    @Column(nullable = false)
    private String description;

    private Boolean status;
}
