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
    private String question;
    private String answer;
    @Enumerated(EnumType.STRING)
    private DifficultyEnum difficulty;
}
