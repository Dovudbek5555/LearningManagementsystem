package com.example.test_system.payload;

import com.example.test_system.entity.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RatingBySumCorrectCount {
    private User student;
    private Integer sumCorrectCount;
}
