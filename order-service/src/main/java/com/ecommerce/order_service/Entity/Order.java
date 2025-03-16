package com.ecommerce.order_service.Entity;

import com.ecommerce.order_service.Entity.ENUM.Status;
import com.ecommerce.order_service.ipc.Product;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private Long userId;
    private List<Product> orderProducts;
    private Status status;
    private LocalDate date;
    private String address;
    private Long totalAmount;
}
