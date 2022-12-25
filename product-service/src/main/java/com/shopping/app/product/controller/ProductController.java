package com.shopping.app.product.controller;

import com.shopping.app.product.dto.ProductRequest;
import com.shopping.app.product.dto.ProductResponse;
import com.shopping.app.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @PostMapping("/api/product")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewProduct(@RequestBody ProductRequest productRequest){
        productService.createProduct(productRequest);
    }
    @GetMapping("/api/product")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> viewAllProducts(){
        return productService.getAllProducts();
    }
}
