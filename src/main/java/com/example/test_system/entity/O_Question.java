package com.example.test_system.entity;

import com.example.test_system.entity.enums.DifficultyEnum;
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

    @Column(unique = true, nullable = false)
    private String question;

    @OneToMany
    private List<Option> optionList;

    @ManyToOne
    private SubCategory subCategory;

    @Enumerated(EnumType.STRING)
    private DifficultyEnum difficulty;
}
