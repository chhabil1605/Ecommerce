package com.ecommerce.seller_service.Controller;


import com.ecommerce.seller_service.Entity.SellerDetails;
import com.ecommerce.seller_service.Request.BankAccountRequest;
import com.ecommerce.seller_service.Service.Interface.SellerService;
import com.ecommerce.seller_service.ipc.UserClient;
import com.ecommerce.seller_service.ipc.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    private UserClient userClient;

    @Autowired
    private SellerService sellerService;

    @PostMapping("/addDetails/{userId}")
    public void addSellerDetails(@PathVariable Long userId,
                                 @RequestBody SellerDetails sellerDetails,
                                 @RequestBody BankAccountRequest bankAccountRequest)
    {
        UserDto userDto= userClient.getUserById(userId);
        if(userDto.getEmail()==null)
        {
            return;
        }
        sellerService.saveSellerDetails(userId,sellerDetails,bankAccountRequest);
    }
}
