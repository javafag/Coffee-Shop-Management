package com.example.Coffee.Shop.Management.repository;

import com.example.Coffee.Shop.Management.entity.CsfOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface OrderRepository extends JpaRepository<CsfOrder, Long> {
    @Query("SELECT SUM(o.price) FROM CsfOrder o WHERE o.waiter.id = :waiterId")
    BigDecimal getTotalSalesByWaiterId(@Param("waiterId") Long id);
}