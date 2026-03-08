package com.example.Coffee.Shop.Management.repository;

import com.example.Coffee.Shop.Management.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
