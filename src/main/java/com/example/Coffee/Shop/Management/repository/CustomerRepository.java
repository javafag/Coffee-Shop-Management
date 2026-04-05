package com.example.Coffee.Shop.Management.repository;

import com.example.Coffee.Shop.Management.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByNameContainingIgnoreCase(String name);
    List<Customer> findByNameAndEmail(String name, String email);
    java.util.Optional<Customer> findByName(String name);
}
