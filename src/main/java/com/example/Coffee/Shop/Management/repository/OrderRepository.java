package com.example.Coffee.Shop.Management.repository;

import com.example.Coffee.Shop.Management.entity.CsfOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<CsfOrder, Long> {
    @Query("SELECT SUM(o.price) FROM CsfOrder o WHERE o.waiter.id = :waiterId")
    BigDecimal getTotalSalesByWaiterId(@Param("waiterId") Long id);

    @Query("SELECT o FROM CsfOrder o JOIN o.orderItems oi WHERE oi.menuItem.id = :drinkName")
    List<CsfOrder> findOrdersByDrinkName(@Param("drinkName") String drinkName);

    @Query("SELECT o.waiter.name, COUNT (O) FROM CsfOrder o GROUP BY o.waiter.name")
    List<Object[]> getSalesByWaiter();

    @Query("SELECT oi.menuItem.name, SUM(oi.quantity) FROM OrderItems oi GROUP BY SUM(oi.quantity) DESC")
    List<Object[]> getMostPopularDrinks();

    List<CsfOrder> findByCustomerIdAndStatus(Long customerId, String status);
    List<CsfOrder> findByWaiterIdAndOrderDateBetween(Long waiterId, LocalDateTime start, LocalDateTime end);
    List<CsfOrder> findByStatusIn(List<String> statuses);
    List<CsfOrder> findByPriceGreaterThan(BigDecimal minPrice);

}