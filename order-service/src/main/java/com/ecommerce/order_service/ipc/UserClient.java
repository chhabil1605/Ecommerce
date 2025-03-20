package com.ecommerce.order_service.ipc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/user/getAddress/{userId}")
    String getAddress(Long userId);
}
