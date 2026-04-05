package com.example.Coffee.Shop.Management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessSummaryDto {
    private BigDecimal totalRevenue;
    private Long totalOrders;
    private List<PopularDrinkDto> mostPopularDrinks;
    private List<WaiterStatsDto> waiterStats;
}
