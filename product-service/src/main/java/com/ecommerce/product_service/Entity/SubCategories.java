package com.ecommerce.product_service.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class SubCategories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subCategoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
}
