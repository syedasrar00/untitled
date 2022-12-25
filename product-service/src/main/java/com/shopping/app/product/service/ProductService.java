package com.shopping.app.product.service;

import com.shopping.app.product.dto.ProductRequest;
import com.shopping.app.product.dto.ProductResponse;
import com.shopping.app.product.model.Product;
import com.shopping.app.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepo;
    public void createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .id(UUID.randomUUID().toString())
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        productRepo.save(product);
        log.info("Product {} is saved",product.getId());
    }

    public List<ProductResponse> getAllProducts() {
        return productRepo.findAll().stream().map(e->
             ProductResponse.builder()
                     .id(e.getId())
                     .name(e.getName())
                     .description(e.getDescription())
                     .price(e.getPrice())
                     .build()
        ).collect(Collectors.toList());
    }
}
