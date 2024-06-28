package com.example.test_system.payload;

import com.example.test_system.entity.Answer;
import com.example.test_system.entity.Test;
import com.example.test_system.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultDto {

    private Integer id;

    //    Current user b/n topiladi
    private UUID studentId;

    private Integer testId;

    private LocalTime startTime;

    private LocalTime endTime;

    private Boolean checked;

}
