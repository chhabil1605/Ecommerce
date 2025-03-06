package com.ecommerce.product_service.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Double price;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ProductImage> productImages;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private InStock inStock;

    private String categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    private SubCategories subCategories;
}
