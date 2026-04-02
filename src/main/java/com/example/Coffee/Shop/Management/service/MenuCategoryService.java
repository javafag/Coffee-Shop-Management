package com.example.Coffee.Shop.Management.service;


import com.example.Coffee.Shop.Management.dto.MenuCategoryDto;
import com.example.Coffee.Shop.Management.entity.MenuCategory;
import com.example.Coffee.Shop.Management.repository.MenuCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuCategoryService {
    private final MenuCategoryRepository menuCategoryRepository;

    private MenuCategoryDto toDto(MenuCategory entity) {
        MenuCategoryDto dto = new MenuCategoryDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDisplayOrder(entity.getDisplayOrder());
        return dto;
    }

    private MenuCategory toEntity(MenuCategoryDto dto) {
        MenuCategory entity = new MenuCategory();
        entity.setName(dto.getName());
        entity.setDisplayOrder(dto.getDisplayOrder());
        return entity;
    }

    public MenuCategoryDto createCategory(MenuCategoryDto dto) {
        MenuCategory entity = toEntity(dto);
        entity = menuCategoryRepository.save(entity);
        return toDto(entity);
    }

    public void deleteCategory(Long id) {
        MenuCategory category = menuCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if(!category.getMenuItems().isEmpty()) {
            throw new RuntimeException("Category has items");
        }else{
            menuCategoryRepository.delete(category);
        }
    }

    public List<MenuCategoryDto> getAllCategories() {
        List<MenuCategory> categories = menuCategoryRepository.findAll();
        return categories.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
