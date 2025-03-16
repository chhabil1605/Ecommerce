package com.ecommerce.seller_service.ipc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/user/getUser/{userId}")
    UserDto getUserById(@PathVariable Long userId);
}
