package com.ecommerce.product_service.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class InStock {
    @OneToOne
    @JoinColumn(name="product_id",referencedColumnName = "id")
    private Long productId;
    private Integer quantity;
}
