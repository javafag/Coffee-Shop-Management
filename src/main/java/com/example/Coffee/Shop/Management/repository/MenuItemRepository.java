package com.example.Coffee.Shop.Management.repository;

import com.example.Coffee.Shop.Management.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.math.BigDecimal;
import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByCategoryNameAndPriceBetween(String categoryName, BigDecimal min, BigDecimal max);

    List<MenuItem> findByNameContainingIgnoreCase(String name);

    List<MenuItem> findByCategoryId(Long categoryId);

    List<MenuItem> findByCategoryIdAndNameContainingIgnoreCase(Long categoryId, String name);
}