package com.example.test_system.payload;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExamDto {
    private Integer id;
    private Integer groupId;
    private Integer testId;
    private LocalDate startDate;
    private LocalDate finishDate;
}
