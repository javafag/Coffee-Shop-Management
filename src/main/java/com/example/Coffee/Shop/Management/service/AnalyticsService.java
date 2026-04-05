package com.example.Coffee.Shop.Management.service;

import com.example.Coffee.Shop.Management.dto.BusinessSummaryDto;
import com.example.Coffee.Shop.Management.dto.PopularDrinkDto;
import com.example.Coffee.Shop.Management.dto.WaiterStatsDto;
import com.example.Coffee.Shop.Management.entity.Order;
import com.example.Coffee.Shop.Management.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
