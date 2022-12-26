package com.shopping.app.inventoryservice.service;

import com.shopping.app.inventoryservice.dto.InventoryResponse;
import com.shopping.app.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCodes){
        return inventoryRepository.findBySkuCodeIn(skuCodes)
                .stream().map(e->
                        InventoryResponse.builder()
                                .skuCode(e.getSkuCode())
                                .isInStock(0<e.getQuantity())
                                .build()
                        ).collect(Collectors.toList());
    }
}
