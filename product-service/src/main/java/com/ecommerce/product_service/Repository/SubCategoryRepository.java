package com.ecommerce.product_service.Repository;

import com.ecommerce.product_service.Entity.SubCategories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubCategoryRepository extends JpaRepository<SubCategories, Long> {
    boolean findBySubCategoryName(String subCategoryName);

    Optional<SubCategories> findBySubCategoryNameAndCategoryId(String subCategoryName, Long id);
}
