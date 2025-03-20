package com.ecommerce.pdf_service.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "invoice")
@Data
public class Invoice {
    @Id
    private String id;
    private String invoiceNo;
    private String orderId;
    private String customerName;
    private String date;
    private String product;
    private double amount;
    private byte[] pdfData;
}
