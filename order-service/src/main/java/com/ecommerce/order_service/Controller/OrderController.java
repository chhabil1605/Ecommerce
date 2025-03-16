package com.ecommerce.order_service.Controller;

import com.ecommerce.order_service.Service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @PostMapping("/addOrder/{userId}")
    public String addOrder(@PathVariable Long userId, @RequestBody String address) {
        return orderService.addOrder(userId, address);
    }
}
