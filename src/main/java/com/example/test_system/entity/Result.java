package com.example.test_system.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
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
    private User studentId;

    @ManyToOne
    private Test testId;

    @OneToMany
    private List<Answer> answer;

    @Column(nullable = false)
    private Timestamp startTime;

    @Column(nullable = false)
    private Timestamp endTime;

    private Boolean checked;
}
