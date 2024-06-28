package com.example.test_system.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupDto {
    private Integer id;
    private String name;
    private Integer categoryId;
    private Integer teacherId;
}
