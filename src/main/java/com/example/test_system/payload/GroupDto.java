package com.example.test_system.payload;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupDto {
    private Integer id;
    private String name;
    private Integer categoryId;
    private UUID teacherId;
    private LocalDate createdAt;
}
