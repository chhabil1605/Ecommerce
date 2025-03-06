package com.ecommerce.product_service.Repository;

import com.ecommerce.product_service.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryName(String categoryName);

    Long findIdByCategoryName(String categoryName);
}

