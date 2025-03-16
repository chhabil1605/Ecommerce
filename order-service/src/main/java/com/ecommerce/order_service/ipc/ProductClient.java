package com.ecommerce.order_service.ipc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/product/getFavouriteProducts/{productIds}")
    List<Product> getFavouriteProducts(List<Long> productIds);

    @GetMapping("/products/isProductExist/{productId}")
    boolean isProductExists(Long productId);

    @PostMapping("/product/getProductById/{productId}")
    Product getProductById(Long productId);

    @PostMapping("/productRemaining/{productId}")
    public Long productRemaining(Long productId);
}
