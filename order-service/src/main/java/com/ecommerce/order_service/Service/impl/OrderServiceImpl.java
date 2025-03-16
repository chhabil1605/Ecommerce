package com.ecommerce.order_service.Service.impl;

import com.ecommerce.order_service.Entity.Cart;
import com.ecommerce.order_service.Entity.ENUM.Status;
import com.ecommerce.order_service.Entity.Order;
import com.ecommerce.order_service.Repository.CartRepository;
import com.ecommerce.order_service.Repository.OrderRepository;
import com.ecommerce.order_service.Service.interfaces.OrderService;
import com.ecommerce.order_service.ipc.Product;
import com.ecommerce.order_service.ipc.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserClient userClient;

    @Override
    public String addOrder(Long userId, String address) {
        if(!cartRepository.existsByUserId(userId))
            throw new RuntimeException("Please add products to cart");
        Cart cart =cartRepository.findByUserId(userId);
        List<Product> products=cart.getProducts();

        // Calculate total amount
        long totalAmount = products.stream()
                .mapToLong(product -> product.getPrice() != null ? product.getPrice().longValue() : 0) // Ensure price is not null
                .sum();

        Order order = new Order();
        order.setUserId(userId);
        order.setOrderProducts(products);
        order.setStatus(Status.ORDERED);
        order.setDate(java.time.LocalDate.now());
        order.setAddress(address);
        order.setTotalAmount(totalAmount);
        orderRepository.save(order);
        cartRepository.deleteByUserId(userId);
        return "Product Ordered";
    }

    @PostMapping("/selectAddress/{userId}")
    public String selectAddress(@PathVariable Long userId) {
        return userClient.getAddress(userId);
    }
}
