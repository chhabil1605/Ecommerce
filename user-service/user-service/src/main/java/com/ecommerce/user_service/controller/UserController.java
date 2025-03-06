package com.ecommerce.user_service.controller;

import com.ecommerce.user_service.request.AddressRequest;
import com.ecommerce.user_service.request.LoginRequest;
import com.ecommerce.user_service.request.SignUpRequest;
import com.ecommerce.user_service.response.ApiResponse;
import com.ecommerce.user_service.response.LoginResponse;
import com.ecommerce.user_service.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sign-up")
    public LoginResponse signUp(@RequestBody SignUpRequest signUpRequest) {
     return userService.signUp(signUpRequest);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @PostMapping("/addAddress/{userId}")
    public ApiResponse addAddress(@RequestBody AddressRequest addressRequest, @PathVariable Long userId) {
        return userService.addAddress(addressRequest,userId);
    }
}
