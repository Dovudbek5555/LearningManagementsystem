package com.example.test_system.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.security.Timestamp;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Collection collection;
    private LocalDate startDate;
    private LocalDate finishDate;
    private Timestamp timeLimit;
    private Integer passingScore;
    @ManyToOne
    private SubCategory subCategory;
    @ManyToOne
    private Group group;
}
