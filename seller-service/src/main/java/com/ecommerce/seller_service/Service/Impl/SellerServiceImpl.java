package com.ecommerce.seller_service.Service.Impl;

import com.ecommerce.seller_service.Entity.BankAccount;
import com.ecommerce.seller_service.Entity.SellerDetails;
import com.ecommerce.seller_service.Repository.SellerDetailsRepository;
import com.ecommerce.seller_service.Request.BankAccountRequest;
import com.ecommerce.seller_service.Service.Interface.SellerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerDetailsRepository sellerDetailsRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public String saveSellerDetails(Long userId, SellerDetails sellerDetails, BankAccountRequest bankAccountRequest) {
        Optional<SellerDetails> sellerDetails1 =sellerDetailsRepository.findById(userId);
        if(sellerDetails1.isPresent())
        {
            return "Details already exists";
        }
        SellerDetails details=new SellerDetails();
        details.setUserId(userId);
        if(isValidGstNumber(sellerDetails.getGstNo()) && isValidPanNumber(sellerDetails.getPanCardNo()))
        {
            details.setGstNo(sellerDetails.getGstNo());
            details.setPanCardNo(sellerDetails.getPanCardNo());
        }
        else {
            return "GST number or Pan Card number is invalid";
        }


        if(isValidAccount(bankAccountRequest.getAccountNo())&& isValidIfscCode(bankAccountRequest.getIFSC_Code()))
        {
            BankAccount account=mapper.map(bankAccountRequest,BankAccount.class);
            details.setBankAccount(account);
        }
        else {
            return "Invalid Bank Details";
        }
        

        sellerDetailsRepository.save(details);
        return "Seller Bank Details Saved Successfully";
    }

    private boolean isValidIfscCode(String ifscCode) {
        return ifscCode != null && ifscCode.matches("^[A-Z]{4}0[A-Z0-9]{6}$");
    }

    private boolean isValidAccount(String accountno) {
        return accountno != null && accountno.matches("\\d{9,18}");
    }


    private boolean isValidPanNumber(String panNumber) {
        return panNumber != null && panNumber.matches("^[A-Z]{5}[0-9]{4}[A-Z]$");
    }

    private boolean isValidGstNumber(String gstNumber) {
        return gstNumber != null && gstNumber.matches("^[0-9]{2}[A-Z]{4}[0-9]{4}[A-Z][0-9]$");
    }
}
