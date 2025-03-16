package com.ecommerce.seller_service.Service.Interface;

import com.ecommerce.seller_service.Entity.SellerDetails;
import com.ecommerce.seller_service.Request.BankAccountRequest;

public interface SellerService {
    String saveSellerDetails(Long userId, SellerDetails sellerDetails, BankAccountRequest bankAccountRequest);
}
