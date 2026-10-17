package com.example.invoice;

import java.math.BigDecimal;

/** New-gen (Java 16+) record. Keeps the legacy getters so existing callers compile. */
public record LineItem(String product, int quantity, BigDecimal unitPrice) {

    public String getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public BigDecimal lineTotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
