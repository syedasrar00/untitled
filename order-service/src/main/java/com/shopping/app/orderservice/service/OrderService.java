package com.shopping.app.orderservice.service;

import com.shopping.app.orderservice.dto.OrderRequest;
import com.shopping.app.orderservice.model.Order;
import com.shopping.app.orderservice.model.OrderItems;
import com.shopping.app.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;
    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderItemsList(orderRequest.
                getOrderItemsDtos()
                .stream()
                .map(e->
                        OrderItems.builder()
                                .order(order)
                                .price(e.getPrice())
                                .quantity(e.getQuantity())
                                .build()).collect(Collectors.toList()));
        orderRepository.save(order);
    }
}
