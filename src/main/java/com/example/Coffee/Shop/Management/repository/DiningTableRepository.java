package com.example.Coffee.Shop.Management.repository;

import com.example.Coffee.Shop.Management.entity.DiningTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiningTableRepository extends JpaRepository<DiningTable, Long> {

    Optional<DiningTable> findByTableNumber(String tableNumber);

    List<DiningTable> findByIsActiveTrue();

    List<DiningTable> findByCapacityGreaterThanEqual(Integer minCapacity);

}
