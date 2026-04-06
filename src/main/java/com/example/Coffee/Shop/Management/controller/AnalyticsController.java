package com.example.Coffee.Shop.Management.controller;

import com.example.Coffee.Shop.Management.dto.BusinessSummaryDto;
import com.example.Coffee.Shop.Management.dto.PopularDrinkDto;
import com.example.Coffee.Shop.Management.dto.WaiterStatsDto;
import com.example.Coffee.Shop.Management.service.AnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
@Tag(name = "Analytics", description = "Endpoints for business analytics and reports")
@PreAuthorize("hasRole('ADMIN')")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/popular-drinks")
    @Operation(summary = "Get a list of the most popular drinks by quantity sold")
    public List<PopularDrinkDto> getMostPopularDrinks() {
        return analyticsService.getMostPopularDrinks();
    }

    @GetMapping("/waiter-stats")
    @Operation(summary = "Get performance stats for all waiters")
    public List<WaiterStatsDto> getWaiterStats() {
        return analyticsService.getWaiterStats();
    }

    @GetMapping("/summary")
    @Operation(summary = "Get an overall business summary including revenue and top sellers")
    public BusinessSummaryDto getBusinessSummary() {
        return analyticsService.getBusinessSummary();
    }

    @GetMapping("/revenue-chart")
    @Operation(summary = "Get daily revenue data for charts")
    public List<com.example.Coffee.Shop.Management.dto.DailyRevenueDto> getRevenueChart() {
        return analyticsService.getRevenueChart();
    }
}
