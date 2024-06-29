package com.example.test_system.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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

    @OneToMany
    private List<Question> questionList;

    @CreatedDate
    private LocalDate createdAt;

    @CreatedBy
    private UUID teacherId;

    //integer da minut kiritiladi keyin Duration.minutOf(integer)
    private Duration duration;

    @Column(nullable = false)
    private Integer passingScore;

    @ManyToOne
    private SubCategory subCategory;

}
