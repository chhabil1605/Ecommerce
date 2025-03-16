package com.ecommerce.order_service.Controller;

import com.ecommerce.order_service.Service.interfaces.FavouritesService;
import com.ecommerce.order_service.ipc.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favourites")
public class FavouritesController {

    @Autowired
    private FavouritesService favouritesService;

    @PostMapping("/addFavourite/{userId}/{productId}")
    public String addFavourite(@PathVariable Long userId, @PathVariable Long productId) {
        return favouritesService.addFavourite(userId, productId);
    }

    @GetMapping("/getFavourites/{userId}")
    public List<Product> getFavourites(@PathVariable Long userId) {
        List<Long> productIds= favouritesService.getProductsList(userId);
        if (productIds.isEmpty())
            throw new RuntimeException("No favourites found");
        return favouritesService.getFavourites(productIds);
    }
}
