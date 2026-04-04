package com.example.Coffee.Shop.Management.controller;

import com.example.Coffee.Shop.Management.dto.DiningTableRequestDto;
import com.example.Coffee.Shop.Management.dto.DiningTableResponseDto;
import com.example.Coffee.Shop.Management.service.DiningTableService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tables")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DiningTableController {
    private final DiningTableService diningTableService;

    @PostMapping
    public ResponseEntity<DiningTableResponseDto> createTable(@Valid @RequestBody DiningTableRequestDto dto) {
        DiningTableResponseDto result = diningTableService.createTable(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping
    public ResponseEntity<List<DiningTableResponseDto>> getAllTables() {
        List<DiningTableResponseDto> tables = diningTableService.getAllTables();
        return ResponseEntity.ok(tables);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiningTableResponseDto> getTableById(@PathVariable Long id) {
        DiningTableResponseDto table = diningTableService.getTableById(id);
        return ResponseEntity.ok(table);
    }

    @GetMapping("/available")
    public ResponseEntity<List<DiningTableResponseDto>> getAvailableTables() {
        List<DiningTableResponseDto> tables = diningTableService.getAvailableTables();
        return ResponseEntity.ok(tables);
    }

    @GetMapping("/capacity/{minCapacity}")
    public ResponseEntity<List<DiningTableResponseDto>> getTablesByCapacity(@PathVariable Integer minCapacity) {
        List<DiningTableResponseDto> tables = diningTableService.getTablesByCapacity(minCapacity);
        return ResponseEntity.ok(tables);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiningTableResponseDto> updateTable(@PathVariable Long id, @Valid @RequestBody DiningTableRequestDto dto) {
        DiningTableResponseDto result = diningTableService.updateTable(id, dto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTable(@PathVariable Long id) {
        diningTableService.deleteTable(id);
        return ResponseEntity.noContent().build();
    }
}
