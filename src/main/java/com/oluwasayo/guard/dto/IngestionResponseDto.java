package com.oluwasayo.guard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngestionResponseDto<T> {
    private String status;
    private String message;
    private T data;
    private Long executionTime;
}
