package com.ecommerce.user_service.repository;

import com.ecommerce.user_service.Entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
