package com.tesis.aike.model.dto;

import lombok.Data;
import java.util.List;

@Data
public class CartPaymentRequestDTO {
    private List<CartItemRequestDTO> items;
    private Long userId;
    private String payerEmail;

    public List<CartItemRequestDTO> getItems() {
        return items;
    }

    public void setItems(List<CartItemRequestDTO> items) {
        this.items = items;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPayerEmail() {
        return payerEmail;
    }

    public void setPayerEmail(String payerEmail) {
        this.payerEmail = payerEmail;
    }
}