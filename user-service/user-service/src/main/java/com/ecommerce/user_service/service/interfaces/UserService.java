package com.ecommerce.user_service.service.interfaces;

import com.ecommerce.user_service.request.AddressRequest;
import com.ecommerce.user_service.request.LoginRequest;
import com.ecommerce.user_service.request.SignUpRequest;
import com.ecommerce.user_service.response.ApiResponse;
import com.ecommerce.user_service.response.LoginResponse;

public interface UserService {

    LoginResponse signUp(SignUpRequest signUpRequest);

    LoginResponse login(LoginRequest loginRequest);

    ApiResponse addAddress(AddressRequest addressRequest,Long userId);

    String getAddress(Long userId);

//    ApiResponse updateAddress(AddressRequest addressRequest,Long userId,Long addressId);
}
