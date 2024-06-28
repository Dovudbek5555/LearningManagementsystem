package com.example.test_system.payload;

import com.example.test_system.entity.Question;
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
    private Integer questionId;
    private LocalDate createdAt;
    private Integer duration;
    private Integer passingScore;
    private Integer subCategoryId;
    private List<Question> oQuestion;
}
