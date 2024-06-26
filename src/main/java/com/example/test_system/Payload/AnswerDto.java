package com.example.test_system.Payload;

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
