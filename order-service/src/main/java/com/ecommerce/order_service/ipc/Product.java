package com.ecommerce.order_service.ipc;

import lombok.Data;

import java.util.List;

@Data
public class Product {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private List<ProductImage> productImages;
    private Long quantity;
}
