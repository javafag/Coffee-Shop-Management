package com.example.Coffee.Shop.Management.repository;


import com.example.Coffee.Shop.Management.entity.Waiter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WaiterRepository extends JpaRepository<Waiter, Long> {
    Optional<Waiter> findByName(String name);
}
