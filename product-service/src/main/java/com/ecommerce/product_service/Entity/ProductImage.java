package com.ecommerce.product_service.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ProductImage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob // This annotation is used to indicate that the field can hold large data
    private byte[] imageData;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "product_id")
    private Product product;
}
