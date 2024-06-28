package com.example.test_system.entity;

import com.example.test_system.entity.enums.DifficultyEnum;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String question;

    @ManyToOne
    private SubCategory subCategory;

    @Enumerated(EnumType.STRING)
    private DifficultyEnum difficulty;
}
