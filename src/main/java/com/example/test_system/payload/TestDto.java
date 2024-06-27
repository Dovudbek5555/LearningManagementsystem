package com.example.test_system.payload;

import com.example.test_system.entity.QuestionList;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestDto {
    private Integer id;
    private Integer questionListId;
    private LocalDate startActiveDate;
    private LocalDate finishActiveDate;
    private Integer duration;
    private Integer passingScore;
    private Integer subCategoryId;
    private Integer groupId;
}
