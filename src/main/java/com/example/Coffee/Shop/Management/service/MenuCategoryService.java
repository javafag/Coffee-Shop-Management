package com.example.Coffee.Shop.Management.service;


import com.example.Coffee.Shop.Management.dto.MenuCategoryDto;
import com.example.Coffee.Shop.Management.entity.MenuCategory;
import com.example.Coffee.Shop.Management.repository.MenuCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuCategoryService {
    private final MenuCategoryRepository menuCategoryRepository;
    private final ModelMapper modelMapper;

    private MenuCategoryDto toDto(MenuCategory entity) {
        return modelMapper.map(entity, MenuCategoryDto.class);
    }

    private MenuCategory toEntity(MenuCategoryDto dto) {
        return modelMapper.map(dto, MenuCategory.class);
    }

    public MenuCategoryDto createCategory(MenuCategoryDto dto) {
        MenuCategory entity = toEntity(dto);
        entity = menuCategoryRepository.save(entity);
        return toDto(entity);
    }

    public MenuCategoryDto getCategoryById(Long id) {
        MenuCategory entity = menuCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return toDto(entity);
    }

    public MenuCategoryDto updateCategory(Long id, MenuCategoryDto dto) {
        MenuCategory entity = menuCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        
        modelMapper.map(dto, entity);
        MenuCategory saved = menuCategoryRepository.save(entity);
        return toDto(saved);
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
