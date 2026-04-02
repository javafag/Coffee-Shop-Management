package com.example.Coffee.Shop.Management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiningTableResponseDto {
    private Long id;
    private String tableNumber;
    private Integer capacity;
    private Boolean isAvailable;
    private Boolean isReserved;
    private Boolean isActive;
}

