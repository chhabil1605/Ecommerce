package com.ecommerce.product_service.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class InStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="product_id",referencedColumnName = "id")
    private Product product;
    private Integer quantity;



    public Integer getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Integer quantity)
    {
        this.quantity = quantity;
    }
}
