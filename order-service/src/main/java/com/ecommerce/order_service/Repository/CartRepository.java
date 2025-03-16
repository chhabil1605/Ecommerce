package com.ecommerce.order_service.Repository;

import com.ecommerce.order_service.Entity.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartRepository extends MongoRepository<Cart,Long> {
    boolean existsByUserId(Long userId);

    Cart findByUserId(Long userId);

    void deleteByUserId(Long userId);
}
