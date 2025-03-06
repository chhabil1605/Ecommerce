package com.ecommerce.product_service.Request;

import lombok.Data;

@Data
public class AddProduct {
    private String productName;
    private String description;
    private Double price;
    private String imageUrl;
    private Integer quantity;
    private String categoryName;
    private String subCategoryName;
}
