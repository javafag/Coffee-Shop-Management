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

@Transactional
@Service
@RequiredArgsConstructor
public class OrderService {


    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final WaiterRepository waiterRepository;


    public OrderResponseDto createOrder(OrderRequestDto request) {

        String nameFromDto = request.getWaiterName();

        Waiter waiter = waiterRepository.findByName(nameFromDto)
                .orElseGet(() -> {
                    Waiter newWaiter = Waiter.builder()
                            .name(request.getWaiterName())
                            .build();
                    return waiterRepository.save(newWaiter);
                        });



        Customer customer = Customer.builder()
                .name(request.getCustomerName())
                .build();


        Customer savedCustomer = customerRepository.save(customer);





        Order order = Order.builder()
                .waiter(waiter)
                .customer(savedCustomer)
                .drinkName(request.getDrinkName())
                .status(request.getStatus())
                .price(request.getPrice())
                .build();

        Order savedOrder = orderRepository.save(order);


        return OrderResponseDto.builder()
                .id(savedOrder.getId())
                .drinkName(savedOrder.getDrinkName())
                .status(savedOrder.getStatus())
                .price(savedOrder.getPrice())
                .customerName(savedOrder.getCustomer().getName())
                .build();
    }




    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "order not found");
        }
        orderRepository.deleteById(id);
    }


}