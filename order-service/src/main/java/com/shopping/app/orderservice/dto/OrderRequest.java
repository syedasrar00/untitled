package com.shopping.app.orderservice.dto;

import com.shopping.app.orderservice.model.OrderItems;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private List<OrderItemsDto> orderItemsDtos;
}
