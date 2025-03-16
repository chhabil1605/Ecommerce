package com.ecommerce.order_service.Service.impl;

import com.ecommerce.order_service.Entity.Favourites;
import com.ecommerce.order_service.Repository.FavouritesRepository;
import com.ecommerce.order_service.Service.interfaces.FavouritesService;
import com.ecommerce.order_service.ipc.ProductClient;
import com.ecommerce.order_service.ipc.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavouritesServiceImpl implements FavouritesService {

    @Autowired
    private FavouritesRepository favouritesRepository;

    @Autowired
    private ProductClient productClient;

    @Override
    public String addFavourite(Long userId, Long productId) {
        if(!productClient.isProductExists(productId))
            return "Product does not exist";
        Favourites favourites = new Favourites();
        favourites.setUserId(userId);
        favourites.setProductId(productId);
        favouritesRepository.save(favourites);
        return "Product added to favourites";
    }

    @Override
    public List<Product> getFavourites(List<Long> productIds) {
    List<Product> products=productClient.getFavouriteProducts(productIds);
        return products;
    }

    @Override
    public List<Long> getProductsList(Long userId) {
       List<Long> productIds = favouritesRepository.findAllProductIdByUserId(userId);
        return productIds;
    }
}
