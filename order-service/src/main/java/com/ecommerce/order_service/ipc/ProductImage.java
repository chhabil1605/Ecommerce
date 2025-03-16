package com.ecommerce.order_service.ipc;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class ProductImage {
    private Long id;
    @Lob // This annotation is used to indicate that the field can hold large data
    private byte[] imageData;


    private Long productId;
}
