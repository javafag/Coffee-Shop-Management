package com.example.Coffee.Shop.Management.repository;

import com.example.Coffee.Shop.Management.entity.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory,Long> {

    Optional<MenuCategory> findByName(String name);
}
