package com.example.test_system.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerDto {
    private Integer id;
    private Integer questionId;
    private String answer;
}
