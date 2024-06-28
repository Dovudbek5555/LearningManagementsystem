package com.example.test_system.payload;

import lombok.*;

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

    private Integer questionId;
}
