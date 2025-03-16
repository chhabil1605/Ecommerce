package com.ecommerce.seller_service.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class SellerDetails {

    @Id
    @Column(unique = true, nullable = false)
    private Long userId;

    private String gstNo;

    @Column(unique = true,nullable = false)
    private String panCardNo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private BankAccount bankAccount;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getGstNo() {
        return gstNo;
    }

    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
    }

    public String getPanCardNo() {
        return panCardNo;
    }

    public void setPanCardNo(String panCardNo) {
        this.panCardNo = panCardNo;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }
}
