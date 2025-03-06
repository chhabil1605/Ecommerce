package com.ecommerce.user_service.response;

import lombok.Data;

@Data
public class LoginResponse {

    private String jwtToken;
    private UserDto userDto;
}
