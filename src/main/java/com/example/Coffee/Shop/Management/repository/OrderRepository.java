package com.example.Coffee.Shop.Management.repository;

import com.example.Coffee.Shop.Management.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {


    @Query("SELECT SUM(o.price) FROM Order o WHERE o.waiter.id = :waiterId")
    BigDecimal getTotalSalesByWaiterId(@Param("waiterId") Long id);


    @Query("SELECT o FROM Order o JOIN o.orderItems oi WHERE oi.menuItem.name = :drinkName")
    List<Order> findOrdersByDrinkName(@Param("drinkName") String drinkName);


    @Query("SELECT o.waiter.name, COUNT(o) FROM Order o GROUP BY o.waiter.name")
    List<Object[]> getSalesByWaiter();



    @Query("SELECT oi.menuItem.name, SUM(oi.quantity) FROM OrderItem oi GROUP BY oi.menuItem.name ORDER BY SUM(oi.quantity) DESC")
    List<Object[]> getMostPopularDrinks();

    @Query("SELECT o FROM Order o WHERE o.customer.id = :customerId AND o.price > :minAmount")
    List<Order> findCustomerOrdersWithAmountGreaterThan(@Param("customerId") Long customerId, @Param("minAmount") BigDecimal minAmount);

    @EntityGraph(attributePaths = {"customer", "orderItems.menuItem"})
    List<Order> findByStatus(String status);

    @EntityGraph(attributePaths = {"waiter", "orderItems.menuItem"})
    List<Order> findByWaiterName(String name);

    @EntityGraph(attributePaths = {"waiter", "customer", "orderItems.menuItem"})
    List<Order> findAll();

    List<Order> findByCustomerIdAndStatus(Long customerId, String status);
    List<Order> findByWaiterIdAndOrderDateBetween(Long waiterId, LocalDateTime start, LocalDateTime end);

    @Query("SELECT cast(o.orderDate as date), SUM(o.price) FROM Order o GROUP BY cast(o.orderDate as date) ORDER BY cast(o.orderDate as date) ASC")
    List<Object[]> getRevenuePerDay();
}
