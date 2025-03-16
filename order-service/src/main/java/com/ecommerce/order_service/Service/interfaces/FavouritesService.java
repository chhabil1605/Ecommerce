package com.ecommerce.order_service.Service.interfaces;

import com.ecommerce.order_service.ipc.Product;

import java.util.List;

public interface FavouritesService {

    String addFavourite(Long userId, Long productId);

    List<Product> getFavourites(List<Long> productIds);

    List<Long> getProductsList(Long userId);
}
