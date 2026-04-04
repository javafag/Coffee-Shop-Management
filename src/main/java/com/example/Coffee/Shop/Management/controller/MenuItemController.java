package com.example.Coffee.Shop.Management.controller;

import com.example.Coffee.Shop.Management.dto.MenuItemDto;
import com.example.Coffee.Shop.Management.service.MenuItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/menu-items")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MenuItemController {
    private final MenuItemService menuItemService;

    @PostMapping
    public ResponseEntity<MenuItemDto> createMenuItem(@Valid @RequestBody MenuItemDto dto) {
        MenuItemDto result = menuItemService.createMenuItem(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping
    public ResponseEntity<List<MenuItemDto>> getAllMenuItems() {
        List<MenuItemDto> items = menuItemService.getAllMenuItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemDto> getMenuItemById(@PathVariable Long id) {
        MenuItemDto item = menuItemService.getMenuItemById(id);
        return ResponseEntity.ok(item);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<MenuItemDto>> getMenuItemsByCategory(@PathVariable Long categoryId) {
        List<MenuItemDto> items = menuItemService.getMenuItemsByCategory(categoryId);
        return ResponseEntity.ok(items);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItemDto> updateMenuItem(@PathVariable Long id, @Valid @RequestBody MenuItemDto dto) {
        MenuItemDto result = menuItemService.updateMenuItem(id, dto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        menuItemService.deleteMenuItem(id);
        return ResponseEntity.noContent().build();
    }
}
