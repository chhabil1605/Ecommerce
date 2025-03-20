package com.ecommerce.pdf_service.Repository;

import com.ecommerce.pdf_service.Entity.Invoice;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InvoiceRepository extends MongoRepository<Invoice, String> {
}
