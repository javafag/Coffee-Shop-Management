package com.example.Coffee.Shop.Management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class CsfWaiterStatsDto {
    private Long waiterId;
    private String waiterName;
    private BigDecimal totalSales;
}
