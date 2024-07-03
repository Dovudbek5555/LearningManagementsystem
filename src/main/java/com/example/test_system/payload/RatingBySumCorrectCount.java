package com.example.test_system.payload;

import com.example.test_system.entity.Exam;
import com.example.test_system.entity.User;
import lombok.*;


@NoArgsConstructor
@Getter
@Setter
@Builder
public class RatingBySumCorrectCount {
    private User student;
    private Long sumCorrectCount;


    public RatingBySumCorrectCount(User student, Long sumCorrectCount) {
        this.student = student;
        this.sumCorrectCount = sumCorrectCount;
    }
}
