package com.example.Coffee.Shop.Management.service;

import com.example.Coffee.Shop.Management.entity.Order;
import com.example.Coffee.Shop.Management.entity.QOrder;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DynamicOrderSearchService {
    private final EntityManager entityManager;





    public List<Order> searchOrders(Long customerId, Long waiterId, String status, BigDecimal minPrice, BigDecimal maxPrice, String sortBy, String sortDir) {

        QOrder order =  QOrder.order;
        JPAQuery<Order> query = new JPAQuery(entityManager);

        query.from(order);

        if (customerId != null) {
            query.where(order.customer.id.eq(customerId));
        }

        if (status != null) {
            query.where(order.status.eq(status));
        }

        if (waiterId != null) {
            query.where(order.waiter.id.eq(waiterId));
        }

        if (status != null){
            query.where(order.status.eq(status));
        }

        if(minPrice != null){
            query.where(order.price.goe(minPrice));
        }

        if(maxPrice != null){
            query.where(order.price.loe(maxPrice));
        }

        if(sortBy != null & sortDir != null){
            if("price".equals(sortBy)){
                if("asc".equals(sortDir)){
                    query.orderBy(order.price.asc());
                }else {
                    query.orderBy(order.price.desc());
                }
            }
        }

        return query.fetch();
    }

}
