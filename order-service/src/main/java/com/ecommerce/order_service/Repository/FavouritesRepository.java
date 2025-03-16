package com.ecommerce.order_service.Repository;

import com.ecommerce.order_service.Entity.Favourites;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavouritesRepository extends JpaRepository<Favourites, Long> {
    List<Long> findAllProductIdByUserId(Long userId);
}
