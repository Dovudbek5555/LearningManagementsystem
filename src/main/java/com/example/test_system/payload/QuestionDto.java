package com.example.test_system.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionDto {

    private Integer id;

    private String question;

    private Integer subCategoryId;

    private String difficultyEnum;

}
