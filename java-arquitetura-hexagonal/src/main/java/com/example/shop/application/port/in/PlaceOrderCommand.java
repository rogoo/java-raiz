package com.example.shop.application.port.in;

import com.example.shop.domain.OrderLine;
import java.util.List;
import java.util.Objects;

public record PlaceOrderCommand(String customerId, List<OrderLine> lines) {

    public PlaceOrderCommand {
        Objects.requireNonNull(customerId, "customerId");
        Objects.requireNonNull(lines, "lines");
        if (customerId.isBlank()) {
            throw new IllegalArgumentException("customerId must not be blank");
        }
        lines = List.copyOf(lines);
    }
}
