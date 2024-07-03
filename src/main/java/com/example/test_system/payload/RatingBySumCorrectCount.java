package com.example.test_system.payload;

import com.example.test_system.entity.Exam;
import com.example.test_system.entity.User;
import lombok.*;

import java.util.UUID;


@NoArgsConstructor
@Getter
@Setter
@Builder
public class RatingBySumCorrectCount {
    private UUID studentId;
    private String studentName;
    private Long sumCorrectCount;

    public RatingBySumCorrectCount(UUID studentId, String studentName, Long sumCorrectCount) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.sumCorrectCount = sumCorrectCount;
    }
}
