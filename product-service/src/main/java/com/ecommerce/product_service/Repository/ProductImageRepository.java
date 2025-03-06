package com.ecommerce.product_service.Repository;

import com.ecommerce.product_service.Entity.Product;
import com.ecommerce.product_service.Entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {
    List<ProductImage> findByProduct(Product product);
}
