package com.example.Coffee.Shop.Management.service;

import com.example.Coffee.Shop.Management.dto.OrderRequestDto;
import com.example.Coffee.Shop.Management.dto.OrderResponseDto;
import com.example.Coffee.Shop.Management.entity.Customer;
import com.example.Coffee.Shop.Management.entity.Order;
import com.example.Coffee.Shop.Management.entity.Waiter;
import com.example.Coffee.Shop.Management.repository.CustomerRepository;
import com.example.Coffee.Shop.Management.repository.OrderRepository;
import com.example.Coffee.Shop.Management.repository.WaiterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderService {


    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final WaiterRepository waiterRepository;
    private final org.springframework.messaging.simp.SimpMessagingTemplate messagingTemplate;

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto request) {

        String nameFromDto = request.getWaiterName();

        Waiter waiter = waiterRepository.findByName(nameFromDto)
                .orElseGet(() -> {
                    Waiter newWaiter = Waiter.builder()
                            .name(request.getWaiterName())
                            .build();
                    return waiterRepository.save(newWaiter);
                        });



        Customer customer = customerRepository.findByName(request.getCustomerName())
                .orElseGet(() -> {
                    Customer newCustomer = Customer.builder()
                            .name(request.getCustomerName())
                            .build();
                    return customerRepository.save(newCustomer);
                });





        Order order = Order.builder()
                .waiter(waiter)
                .customer(customer)
                .drinkName(request.getDrinkName())
                .status(request.getStatus())
                .price(request.getPrice())
                .build();

        Order savedOrder = orderRepository.save(order);


        OrderResponseDto responseDto = OrderResponseDto.builder()
                .id(savedOrder.getId())
                .drinkName(savedOrder.getDrinkName())
                .status(savedOrder.getStatus())
                .price(savedOrder.getPrice())
                .customerName(savedOrder.getCustomer().getName())
                .build();
                
        messagingTemplate.convertAndSend("/topic/orders", responseDto);
        return responseDto;
    }



    @Transactional
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "order not found");
        }
        orderRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public OrderResponseDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "order not found"));

        return OrderResponseDto.builder()
                .id(order.getId())
                .drinkName(order.getDrinkName())
                .status(order.getStatus())
                .price(order.getPrice())
                .build();
    }



    @Transactional
    public OrderResponseDto updateOrder(Long id, OrderRequestDto request) {
        Order order =orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "order not found"));

        if (request.getDrinkName() != null) {
            order.setDrinkName(request.getDrinkName());
        }
        if (request.getStatus() != null) {
            order.setStatus(request.getStatus());
        }
        if (request.getPrice() != null) {
            order.setPrice(request.getPrice());
        }
        if(request.getCustomerName() != null){
            Customer customer = customerRepository.findByName(request.getCustomerName())
                .orElseGet(() -> {
                    Customer newCustomer = Customer.builder()
                            .name(request.getCustomerName())
                            .build();
                    return customerRepository.save(newCustomer);
                });
            order.setCustomer(customer);
        }

        if(request.getWaiterName() != null){
            Waiter waiter = waiterRepository.findByName(request.getWaiterName())
                .orElseGet(() -> {
                    Waiter newWaiter = Waiter.builder()
                            .name(request.getWaiterName())
                            .build();
                    return waiterRepository.save(newWaiter);
                });
            order.setWaiter(waiter);
        }

        Order savedOrder = orderRepository.save(order);

        return OrderResponseDto.builder()
                .id(savedOrder.getId())
                .drinkName(savedOrder.getDrinkName())
                .status(savedOrder.getStatus())
                .price(savedOrder.getPrice())
                .build();
    }



    @Transactional(readOnly = true)
    public List<OrderResponseDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        if(orders.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "order not found");
        }

        return orders.stream()
                .map(order -> OrderResponseDto.builder()
                        .id(order.getId())
                        .drinkName(order.getDrinkName())
                        .status(order.getStatus())
                        .price(order.getPrice())
                        .build())
                .collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public List<OrderResponseDto> getOrdersByStatus(String status) {
        List<Order> orders = orderRepository.findByStatus(status);

        return orders.stream()
                .map(order -> OrderResponseDto.builder()
                        .id(order.getId())
                        .drinkName(order.getDrinkName())
                        .status(order.getStatus())
                        .price(order.getPrice())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDto> getOrdersByWaiterName(String name) {
        List<Order> orders = orderRepository.findByWaiterName(name);

        return orders.stream()
                .map(order -> OrderResponseDto.builder()
                        .id(order.getId())
                        .drinkName(order.getDrinkName())
                        .status(order.getStatus())
                        .price(order.getPrice())
                        .waiterName(order.getWaiter().getName())
                        .build())
                .collect(Collectors.toList());
    }


}