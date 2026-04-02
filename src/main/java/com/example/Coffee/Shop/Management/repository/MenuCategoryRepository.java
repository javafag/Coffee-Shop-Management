package com.example.Coffee.Shop.Management.repository;

import com.example.Coffee.Shop.Management.entity.MenuCategory;
import com.example.Coffee.Shop.Management.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory,Long> {

}
