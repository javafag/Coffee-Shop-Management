package com.example.Coffee.Shop.Management.service;

import com.example.Coffee.Shop.Management.dto.CsfOrderRequestDto;
import com.example.Coffee.Shop.Management.dto.CsfOrderResponseDto;
import com.example.Coffee.Shop.Management.entity.CsfOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CsfOrderServiceTest {

    @Mock
    private CsfRepository repository;

    private CsfOrderService service;

    @BeforeEach
    void setUp() {
        service = new CsfOrderService(repository);
    }

    @Test
    @DisplayName("Успешное создание заказа: маппинг и сохранение")
    void createOrder_ShouldReturnResponseDto_WhenRequestIsValid() {
        // 1. Arrange (Дано)
        CsfOrderRequestDto request = CsfOrderRequestDto.builder()
                .drinkName("Latte")
                .status("NEW")
                .price(5.0)
                .customerName("John")
                .build();

        CsfOrder savedOrder = CsfOrder.builder()
                .id(1L) // База якобы присвоила ID
                .drinkName("Latte")
                .status("NEW")
                .price(5.0)
                .customerName("John")
                .build();

        // Обучаем мок: при сохранении любого CsfOrder возвращать наш savedOrder
        when(repository.save(any(CsfOrder.class))).thenReturn(savedOrder);

        // 2. Act (Когда)
        CsfOrderResponseDto response = service.createOrder(request);

        // 3. Assert (Тогда)
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Latte", response.getDrinkName());
        assertEquals("John", response.getCustomerName());

        // Проверяем, что репозиторий реально вызывался 1 раз
        verify(repository, times(1)).save(any(CsfOrder.class));
    }


    @Test
    @DisplayName("Should delete order")
    void deleteOrder_ShouldReturnResponseDto_WhenRequestIsValid(){

                Long orderId = 99L;
                when(repository.existsById(orderId)).thenReturn(false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            service.deleteOrder(orderId);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("order not found",exception.getReason());

        verify(repository, never()).deleteById(anyLong());
    }

}
