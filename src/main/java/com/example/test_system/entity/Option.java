package com.example.test_system.entity;

import com.example.test_system.entity.enums.OptionEnum;
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

    @ManyToOne
    private O_Question oQuestion;
}
