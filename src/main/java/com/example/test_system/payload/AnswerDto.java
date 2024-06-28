package com.example.test_system.payload;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerDto {

    private Integer id;

    private Integer questionId;

    private Integer optionId;

    private String answer;

    private boolean correct;

}
