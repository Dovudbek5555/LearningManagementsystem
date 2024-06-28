package com.example.test_system.payload;

import com.example.test_system.entity.O_Question;
import com.example.test_system.entity.QuestionList;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

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
    private List<O_Question> oQuestion;
    private List<String> yQuestion;
}
