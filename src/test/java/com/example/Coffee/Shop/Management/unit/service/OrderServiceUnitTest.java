package com.example.Coffee.Shop.Management.unit.service;

import com.example.Coffee.Shop.Management.repository.CustomerRepository;
import com.example.Coffee.Shop.Management.repository.OrderRepository;
import com.example.Coffee.Shop.Management.repository.WaiterRepository;
import com.example.Coffee.Shop.Management.service.OrderService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
    @DisplayName("OrderService Unit Tests")
class OrderServiceUnitTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private WaiterRepository waiterRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeAll
    static void setupAll() {
        System.out.println("Starting all tests");
    }

    @BeforeEach
    void setUp() {
        System.out.println("Setting up test...");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Cleaning up test...");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("Finished all tests");
    }



    @Test
    @DisplayName("Should validate string operations when given strings")
    void should_ValidateStringOperations_WhenGivenStrings() {
        // Given - тестовые строки
        String validOrderName = "Latte";
        String emptyOrderName = "";
        String orderWithSpaces = "  Cappuccino  ";

        // When - выполняем операции со строками
        // Then - проверяем результаты операций
        assertEquals("Latte", validOrderName);
        assertNotEquals("",validOrderName);
        assertTrue(validOrderName.contains("Latte"));
        assertFalse(emptyOrderName.isEmpty());
        assertTrue(orderWithSpaces.trim().length() < orderWithSpaces.length());

        assertAll("Order name validation",
                () -> assertNotNull(validOrderName),
                () -> assertFalse(validOrderName.isBlank()),
                () -> assertTrue(validOrderName.length() > 0)
        );
    }


    @Test
    @DisplayName("Should throw and catch exception when invalid operation")
    void should_ThrowAndCatchException_WhenInvalidOperation() {
        // Given - ожидаемое исключение
        String expectedMessage = "Invalid order name";
        
        // When - выполняем операцию которая бросает исключение
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException(expectedMessage);
        });
        
        // Then - проверяем исключение
        assertEquals(expectedMessage, exception.getMessage());
    }




}