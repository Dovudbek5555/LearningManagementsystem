package com.example.test_system.Entity;

import com.example.test_system.Entity.Enums.DifficultyEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Y_Question {

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
