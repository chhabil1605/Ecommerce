package com.ecommerce.user_service.exception.custom_exception;

public class AddressNotFound extends RuntimeException {
  public AddressNotFound(String message) {
    super(message);
  }
}
