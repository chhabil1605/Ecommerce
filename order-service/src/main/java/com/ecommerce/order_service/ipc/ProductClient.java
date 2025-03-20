package com.ecommerce.order_service.ipc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/product/getFavouriteProducts")
    List<Product> getFavouriteProducts(@RequestParam List<Long> productIds); // Use @RequestParam here

    @GetMapping("/products/isProductExist/{productId}")
    boolean isProductExists(@PathVariable Long productId);

    @GetMapping("/product/getProductById/{productId}")
    Product getProductById(@PathVariable Long productId);

    @PostMapping("/productRemaining/{productId}")
    Long productRemaining(@PathVariable Long productId);
}