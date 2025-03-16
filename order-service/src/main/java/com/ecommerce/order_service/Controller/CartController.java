package com.ecommerce.order_service.Controller;

import com.ecommerce.order_service.Service.impl.CartServiceImpl;
import com.ecommerce.order_service.ipc.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartServiceImpl cartService;

    @PostMapping("/addToCart/{productId}/{quantity}/{userId}")
    public String addToCart(@PathVariable Long productId, @PathVariable Long quantity ,@PathVariable Long userId) {
        return cartService.addToCart(productId,quantity,userId);
    }

    @PostMapping("/checkAvailability/{productId}")
    public Long checkAvailability(@PathVariable Long productId) {
        return cartService.productRemaining(productId);
    }

    @DeleteMapping("/removeFromCart/{productId}/{userId}")
    public String removeFromCart(@PathVariable Long productId, @PathVariable Long userId) {
        return cartService.removeFromCart(productId, userId);
    }

    @DeleteMapping("/reduceQuantity/{productId}/{userId}")
    public String reduceQuantityByOne(@PathVariable Long productId, @PathVariable Long userId) {
        return cartService.reduceQuantityByOne(productId, userId);
    }

    @GetMapping("/getCart/{userId}")
    public List<Product> getCart(@PathVariable Long userId) {
        return cartService.getCart(userId);
    }
}
