package com.example.Coffee.Shop.Management.service;

import com.example.Coffee.Shop.Management.dto.BusinessSummaryDto;
import com.example.Coffee.Shop.Management.dto.PopularDrinkDto;
import com.example.Coffee.Shop.Management.dto.WaiterStatsDto;
import com.example.Coffee.Shop.Management.entity.Order;
import com.example.Coffee.Shop.Management.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final OrderRepository orderRepository;

    public List<PopularDrinkDto> getMostPopularDrinks() {
        List<Object[]> results = orderRepository.getMostPopularDrinks();
        List<PopularDrinkDto> dtos = new ArrayList<>();
        for (Object[] row : results) {
            String name = (String) row[0];
            Long quantity = ((Number) row[1]).longValue();
            dtos.add(new PopularDrinkDto(name, quantity));
        }
        return dtos;
    }

    public List<WaiterStatsDto> getWaiterStats() {
        List<Order> allOrders = orderRepository.findAll();
        return allOrders.stream()
                .filter(o -> o.getWaiter() != null)
                .collect(Collectors.groupingBy(o -> o.getWaiter()))
                .entrySet().stream()
                .map(entry -> {
                    BigDecimal totalSales = entry.getValue().stream()
                            .map(Order::getPrice)
                            .filter(p -> p != null)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    return new WaiterStatsDto(entry.getKey().getId(), entry.getKey().getName(), totalSales);
                })
                .collect(Collectors.toList());
    }

    public List<com.example.Coffee.Shop.Management.dto.DailyRevenueDto> getRevenueChart() {
        List<Object[]> results = orderRepository.getRevenuePerDay();
        List<com.example.Coffee.Shop.Management.dto.DailyRevenueDto> chart = new ArrayList<>();
        for (Object[] row : results) {
            if (row[0] != null && row[1] != null) {
                LocalDate date = ((Date) row[0]).toLocalDate();
                BigDecimal revenue = new BigDecimal(((Number) row[1]).toString());
                chart.add(new com.example.Coffee.Shop.Management.dto.DailyRevenueDto(date, revenue));
            }
        }
        return chart;
    }

    @Cacheable("businessSummary")
    public BusinessSummaryDto getBusinessSummary() {
        List<Order> allOrders = orderRepository.findAll();
        
        BigDecimal totalRevenue = allOrders.stream()
                .map(Order::getPrice)
                .filter(p -> p != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
                
        Long totalOrders = (long) allOrders.size();
        
        return BusinessSummaryDto.builder()
                .totalRevenue(totalRevenue)
                .totalOrders(totalOrders)
                .mostPopularDrinks(getMostPopularDrinks())
                .waiterStats(getWaiterStats())
                .build();
    }
}
