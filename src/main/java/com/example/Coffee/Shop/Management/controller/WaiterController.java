package com.example.Coffee.Shop.Management.controller;

import com.example.Coffee.Shop.Management.dto.WaiterDto;
import com.example.Coffee.Shop.Management.service.WaiterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/waiters")
@RequiredArgsConstructor
@Tag(name = "Waiters", description = "Endpoints for managing waiters")
public class WaiterController {

    private final WaiterService waiterService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new waiter")
    public WaiterDto createWaiter(@RequestBody WaiterDto dto) {
        return waiterService.createWaiter(dto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a waiter by ID")
    public WaiterDto getWaiter(@PathVariable Long id) {
        return waiterService.getWaiter(id);
    }

    @GetMapping
    @Operation(summary = "Get all waiters")
    public List<WaiterDto> getAllWaiters() {
        return waiterService.getAllWaiters();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing waiter")
    public WaiterDto updateWaiter(@PathVariable Long id, @RequestBody WaiterDto dto) {
        return waiterService.updateWaiter(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a waiter")
    public void deleteWaiter(@PathVariable Long id) {
        waiterService.deleteWaiter(id);
    }
}
