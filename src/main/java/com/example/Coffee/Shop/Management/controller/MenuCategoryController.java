package com.example.Coffee.Shop.Management.controller;

import com.example.Coffee.Shop.Management.dto.MenuCategoryDto;
import com.example.Coffee.Shop.Management.service.MenuCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/menu-categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MenuCategoryController {
    private final MenuCategoryService menuCategoryService;

    @PostMapping
    public ResponseEntity<MenuCategoryDto> createCategory(@Valid @RequestBody MenuCategoryDto dto) {
        MenuCategoryDto result = menuCategoryService.createCategory(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping
    public ResponseEntity<List<MenuCategoryDto>> getAllCategories() {
        List<MenuCategoryDto> categories = menuCategoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuCategoryDto> getCategoryById(@PathVariable Long id) {
        MenuCategoryDto category = menuCategoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuCategoryDto> updateCategory(@PathVariable Long id, @Valid @RequestBody MenuCategoryDto dto) {
        MenuCategoryDto result = menuCategoryService.updateCategory(id, dto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        menuCategoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
