package com.example.coffee.shop.management.service;

import com.example.coffee.shop.management.dto.OrderResponseDto;
import com.example.coffee.shop.management.entity.Order;
import com.example.coffee.shop.management.entity.Customer;
import com.example.coffee.shop.management.entity.Waiter;
import com.example.coffee.shop.management.repository.CustomerRepository;
import com.example.coffee.shop.management.repository.OrderRepository;
import com.example.coffee.shop.management.repository.WaiterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    
    @Mock
    private CustomerRepository customerRepository;
    
    @Mock
    private WaiterRepository waiterRepository;

    private OrderService service;

    @BeforeEach
    void setUp() {
        service = new OrderService(orderRepository, customerRepository, waiterRepository);
    }

    @Test
    @DisplayName("Успешное создание заказа: маппинг и сохранение")
    void createOrder_ShouldReturnResponseDto_WhenRequestIsValid() {
        // Arrange (Дано)
        OrderRequestDto request = OrderRequestDto.builder()
                .drinkName("Latte")
                .status("NEW")
                .price(BigDecimal.valueOf(5.0))
                .customerName("John")
                .waiterName("Alice")
                .build();

        Waiter waiter = Waiter.builder()
                .id(1L)
                .name("Alice")
                .build();
        
        Customer savedCustomer = Customer.builder()
                .id(1L)
                .name("John")
                .build();

        Order savedOrder = Order.builder()
                .id(1L)
                .drinkName("Latte")
                .status("NEW")
                .price(BigDecimal.valueOf(5.0))
                .customer(savedCustomer)
                .waiter(waiter)
                .build();

        // Обучаем моки
        when(waiterRepository.findByName("Alice")).thenReturn(Optional.of(waiter));
        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        // Act (Когда)
        OrderResponseDto response = service.createOrder(request);

        // Assert (Тогда)
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Latte", response.getDrinkName());
        assertEquals("John", response.getCustomerName());

        // Проверяем, что репозитории реально вызывались
        verify(customerRepository, times(1)).save(any(Customer.class));
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    @DisplayName("Should delete order successfully")
    void deleteOrder_ShouldDeleteOrder_WhenOrderExists() {
        Long orderId = 99L;
        when(orderRepository.existsById(orderId)).thenReturn(true);
        
        // Act
        assertDoesNotThrow(() -> service.deleteOrder(orderId));
        
        // Assert
        verify(orderRepository, times(1)).deleteById(orderId);
    }

    @Test
    @DisplayName("Should throw exception when order not found")
    void deleteOrder_ShouldThrowException_WhenOrderNotFound() {
        Long orderId = 99L;
        when(orderRepository.existsById(orderId)).thenReturn(false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            service.deleteOrder(orderId);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("order not found", exception.getReason());

        verify(orderRepository, never()).deleteById(anyLong());
    }
}
