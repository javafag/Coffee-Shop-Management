package com.example.Coffee.Shop.Management.service;

import com.example.Coffee.Shop.Management.dto.MenuItemDto;
import com.example.Coffee.Shop.Management.entity.MenuItem;
import com.example.Coffee.Shop.Management.entity.MenuCategory;
import com.example.Coffee.Shop.Management.repository.MenuItemRepository;
import com.example.Coffee.Shop.Management.repository.MenuCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final MenuCategoryRepository menuCategoryRepository;
    private final ModelMapper modelMapper;

    private MenuItemDto toDto(MenuItem entity) {
        MenuItemDto dto = modelMapper.map(entity, MenuItemDto.class);
        dto.setCategory(entity.getCategory().getName());
        return dto;
    }

    private MenuItem toEntity(MenuItemDto dto) {
        MenuCategory category = menuCategoryRepository.findByName(dto.getCategory())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        MenuItem entity = modelMapper.map(dto, MenuItem.class);
        entity.setCategory(category);
        entity.setPrice(BigDecimal.valueOf(dto.getPrice()));
        return entity;
    }

    public MenuItemDto createMenuItem(MenuItemDto dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name cannot be empty");
        }
        if (dto.getPrice() == null || dto.getPrice() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Price must be positive");
        }
        if (dto.getCategory() == null || dto.getCategory().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category cannot be empty");
        }

        MenuItem entity = toEntity(dto);
        MenuItem saved = menuItemRepository.save(entity);
        return toDto(saved);
    }

    public MenuItemDto getMenuItemById(Long id) {
        MenuItem entity = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu item not found"));
        return toDto(entity);
    }

    public List<MenuItemDto> getAllMenuItems() {
        List<MenuItem> items = menuItemRepository.findAll();
        return items.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<MenuItemDto> getMenuItemsByCategory(Long categoryId) {
        MenuCategory category = menuCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        
        List<MenuItem> items = menuItemRepository.findByCategoryId(categoryId);
        return items.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public MenuItemDto updateMenuItem(Long id, MenuItemDto dto) {
        MenuItem entity = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu item not found"));

        if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
            entity.setName(dto.getName());
        }
        if (dto.getPrice() != null && dto.getPrice() > 0) {
            entity.setPrice(BigDecimal.valueOf(dto.getPrice()));
        }
        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }
        if (dto.getCategory() != null && !dto.getCategory().trim().isEmpty()) {
            MenuCategory category = menuCategoryRepository.findByName(dto.getCategory())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
            entity.setCategory(category);
        }

        MenuItem saved = menuItemRepository.save(entity);
        return toDto(saved);
    }

    public void deleteMenuItem(Long id) {
        MenuItem entity = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu item not found"));
        menuItemRepository.delete(entity);
    }
}
