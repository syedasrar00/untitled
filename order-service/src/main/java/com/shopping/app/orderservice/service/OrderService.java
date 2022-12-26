package com.shopping.app.orderservice.service;

import com.shopping.app.orderservice.dto.InventoryResponse;
import com.shopping.app.orderservice.dto.OrderRequest;
import com.shopping.app.orderservice.model.Order;
import com.shopping.app.orderservice.model.OrderItems;
import com.shopping.app.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient webclient;
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

        List<String> skuCodes = order.getOrderItemsList()
                .stream().map(e -> e.getSkuCode()).collect(Collectors.toList());
        InventoryResponse[] inventoryResponses = webclient
                .get()
                .uri("http://localhost:8082/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean isAvailable = Arrays.stream(inventoryResponses).allMatch(inventoryResponse -> inventoryResponse.isInStock());
        if(isAvailable)
            orderRepository.save(order);
        else
            throw new IllegalArgumentException("Product is not in stock, please try again later");
    }
}
