package com.example.Coffee.Shop.Management.integration.repository;

import com.example.Coffee.Shop.Management.entity.Order;
import com.example.Coffee.Shop.Management.entity.Customer;
import com.example.Coffee.Shop.Management.entity.Waiter;
import com.example.Coffee.Shop.Management.repository.OrderRepository;
import com.example.Coffee.Shop.Management.repository.CustomerRepository;
import com.example.Coffee.Shop.Management.repository.WaiterRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@DisplayName("OrderRepository Integration Tests")
class OrderRepositoryIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.driver-class-name", postgres::getDriverClassName);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private WaiterRepository waiterRepository;

    private Customer testCustomer;
    private Waiter testWaiter;

    @BeforeEach
    void setUp() {
        // Очищаем базу перед каждым тестом
        orderRepository.deleteAll();
        customerRepository.deleteAll();
        waiterRepository.deleteAll();

        // Создаем тестовые данные
        testCustomer = customerRepository.save(Customer.builder()
                .name("John Doe")
                .email("john@example.com")
                .build());

        testWaiter = waiterRepository.save(Waiter.builder()
                .name("Alice Smith")
                .build());
    }

    @Test
    @DisplayName("Should save and retrieve order when data is valid")
    void should_SaveAndRetrieveOrder_WhenDataIsValid() {
        // Given - создаем заказ
        Order order = Order.builder()
                .drinkName("Latte")
                .status("NEW")
                .price(BigDecimal.valueOf(5.50))
                .customer(testCustomer)
                .waiter(testWaiter)
                .build();

        // When - сохраняем в базу
        Order savedOrder = orderRepository.save(order);

        // Then - проверяем результат
        assertNotNull(savedOrder.getId());
        assertEquals("Latte", savedOrder.getDrinkName());
        assertEquals("NEW", savedOrder.getStatus());
        assertEquals(BigDecimal.valueOf(5.50), savedOrder.getPrice());

        // Проверяем что заказ можно найти в базе
        Optional<Order> foundOrder = orderRepository.findById(savedOrder.getId());
        assertTrue(foundOrder.isPresent());
        assertEquals("Latte", foundOrder.get().getDrinkName());
    }
}
