package com.example.test_system.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    Current user b/n topiladi
    @ManyToOne
    private User student;

    @ManyToOne
    private Test test;

    @OneToMany
    private List<Answer> answer;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    private Integer correctCount;

    private Boolean passed;

    private Boolean checked;
}
