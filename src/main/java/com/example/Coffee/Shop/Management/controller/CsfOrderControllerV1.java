package com.example.Coffee.Shop.Management.controller;

import com.example.Coffee.Shop.Management.dto.CsfOrderRequestDto;
import com.example.Coffee.Shop.Management.dto.CsfOrderResponseDto;
import com.example.Coffee.Shop.Management.dto.CsfWaiterStatsDto; // Не забудь создать этот DTO!
import com.example.Coffee.Shop.Management.repository.OrderRepository;
import com.example.Coffee.Shop.Management.repository.WaiterRepository;
import com.example.Coffee.Shop.Management.service.CsfOrderService;
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
public class CsfOrderControllerV1 {

    private final CsfOrderService csfService;
    private final OrderRepository orderRepository;
    private final WaiterRepository waiterRepository;


    @PostMapping
    public ResponseEntity<CsfOrderResponseDto> postRequest(@RequestBody @Valid CsfOrderRequestDto request) {
        CsfOrderResponseDto created = csfService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        csfService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/waiters/{id}/stats")
    public ResponseEntity<CsfWaiterStatsDto> showBestWaiterStat(@PathVariable Long id) {
        return waiterRepository.findById(id)
                .map(waiter -> {
                    BigDecimal total = orderRepository.getTotalSalesByWaiterId(id);
                    CsfWaiterStatsDto stats = CsfWaiterStatsDto.builder()
                            .waiterId(waiter.getId())
                            .waiterName(waiter.getName())
                            .totalSales(total != null ? total : BigDecimal.ZERO)
                            .build();

                    return ResponseEntity.ok(stats);
                })

                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}