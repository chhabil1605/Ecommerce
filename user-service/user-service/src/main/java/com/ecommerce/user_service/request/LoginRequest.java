package com.ecommerce.user_service.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
