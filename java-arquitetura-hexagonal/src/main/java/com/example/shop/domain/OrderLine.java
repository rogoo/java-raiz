package com.example.shop.domain;

import java.util.Objects;

public record OrderLine(String sku, int quantity, Money unitPrice) {

    public OrderLine {
        Objects.requireNonNull(sku, "sku");
        Objects.requireNonNull(unitPrice, "unitPrice");
        if (sku.isBlank()) {
            throw new IllegalArgumentException("sku must not be blank");
        }
        if (quantity < 1) {
            throw new IllegalArgumentException("quantity must be at least 1, was " + quantity);
        }
    }

    public Money subtotal() {
        return unitPrice.times(quantity);
    }
}
