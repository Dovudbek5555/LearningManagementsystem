package com.example.test_system.entity;

import com.example.test_system.entity.enums.DifficultyEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;

import java.util.UUID;

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

    @CreatedBy
    private UUID createdById;

    @Enumerated(EnumType.STRING)
    private DifficultyEnum difficulty;
}
