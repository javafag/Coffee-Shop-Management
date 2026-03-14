package com.example.Coffee.Shop.Management.repository;

import com.example.Coffee.Shop.Management.entity.MenuItem;

import java.math.BigDecimal;
import java.util.List;

public interface MenuItemRepository {
    List<MenuItem> findByCategoryAndPriceBetween(String category, BigDecimal min, BigDecimal max);
    List<MenuItem> findByNameContainingIgnoreCase(String name);
}
