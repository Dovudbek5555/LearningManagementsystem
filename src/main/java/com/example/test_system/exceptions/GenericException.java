package com.example.test_system.exceptions;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenericException extends RuntimeException{
    private String message;
    private Integer statusCode;
}