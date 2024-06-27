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
public class O_Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String question;
    @OneToMany
    private List<Option> optionList;

    @Enumerated(EnumType.STRING)
    private DifficultyEnum difficulty;
}
