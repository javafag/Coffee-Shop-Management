package com.example.Coffee.Shop.Management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CsfOrderResponseDto {

    private Long id;
    private String drinkName;
    private String waiterName;
    private String status;
    private java.math.BigDecimal price;
    private String customerName;
}
