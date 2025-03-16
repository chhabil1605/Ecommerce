package com.ecommerce.seller_service.Repository;

import com.ecommerce.seller_service.Entity.SellerDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerDetailsRepository extends JpaRepository<SellerDetails,Long> {
}
