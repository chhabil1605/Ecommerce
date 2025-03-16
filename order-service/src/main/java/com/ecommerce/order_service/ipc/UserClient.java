package com.ecommerce.order_service.ipc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "user-service")
public interface UserClient {

    @PostMapping("/user/getAddress/{userId}")
    String getAddress(Long userId);
}
