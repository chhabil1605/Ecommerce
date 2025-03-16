package com.ecommerce.order_service.Service.interfaces;

import com.ecommerce.order_service.ipc.Product;

import java.util.List;

public interface CartService {

    String addToCart(Long userId,Long quantity, Long productId);

    String removeFromCart(Long productId, Long userId);

    List<Product> getCart(Long userId);

    String reduceQuantityByOne(Long productId, Long userId);

    Long productRemaining(Long productId);
}
