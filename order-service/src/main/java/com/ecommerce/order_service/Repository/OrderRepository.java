package com.ecommerce.order_service.Repository;

import com.ecommerce.order_service.Entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, Long> {
}
