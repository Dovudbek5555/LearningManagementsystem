package com.example.test_system.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToOne
    private Group group;

    @ManyToOne
    private Test test;

    @CreatedDate
    private LocalDate createdAt;

    @CreatedBy
    private UUID createdBy;

    private LocalDate startDate;

    private LocalDate finishDate;

}
