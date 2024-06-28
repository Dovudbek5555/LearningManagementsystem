package com.example.test_system.payload;

import jakarta.persistence.*;
import lombok.*;
import com.example.test_system.entity.enums.OptionEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OptionDto {

    private Integer id;

    private String optionEnum;

    private String description;

    private Boolean status;
}
