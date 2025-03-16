package com.ecommerce.seller_service.Repository;

import com.ecommerce.seller_service.Entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,Long> {
}
