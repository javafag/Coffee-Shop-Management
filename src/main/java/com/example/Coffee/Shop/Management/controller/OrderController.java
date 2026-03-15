package com.example.Coffee.Shop.Management.controller;

import com.example.Coffee.Shop.Management.dto.OrderRequestDto;
import com.example.Coffee.Shop.Management.dto.OrderResponseDto;
import com.example.Coffee.Shop.Management.dto.WaiterStatsDto;
import com.example.Coffee.Shop.Management.repository.OrderRepository;
import com.example.Coffee.Shop.Management.repository.WaiterRepository;
import com.example.Coffee.Shop.Management.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService csfService;
    private final OrderRepository orderRepository;
    private final WaiterRepository waiterRepository;


    @PostMapping
    public ResponseEntity<OrderResponseDto> postRequest(@RequestBody @Valid OrderRequestDto request) {
        OrderResponseDto created = csfService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        csfService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/waiters/{id}/stats")
    public ResponseEntity<WaiterStatsDto> showBestWaiterStat(@PathVariable Long id) {
        return waiterRepository.findById(id)
                .map(waiter -> {
                    BigDecimal total = orderRepository.getTotalSalesByWaiterId(id);
                    WaiterStatsDto stats = WaiterStatsDto.builder()
                            .waiterId(waiter.getId())
                            .waiterName(waiter.getName())
                            .totalSales(total != null ? total : BigDecimal.ZERO)
                            .build();

                    return ResponseEntity.ok(stats);
                })

                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}