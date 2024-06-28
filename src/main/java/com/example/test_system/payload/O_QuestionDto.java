package com.example.test_system.payload;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class O_QuestionDto {

    private Integer id;

    private String question;

    private List<Integer> optionIdList;

    private Integer subCategoryId;

    private String difficultyEnum;

}
