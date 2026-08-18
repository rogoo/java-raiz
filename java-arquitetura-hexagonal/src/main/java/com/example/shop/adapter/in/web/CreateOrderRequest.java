package com.example.shop.adapter.in.web;

import com.example.shop.application.port.in.PlaceOrderCommand;
import com.example.shop.domain.Money;
import com.example.shop.domain.OrderLine;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

/**
 * Wire format. Lives in the adapter so the core never sees JSON concerns.
 */
public record CreateOrderRequest(
        @NotBlank(message = "customerId é obrigatório, ora maissssss") String customerId,
        @NotEmpty @Valid List<Line> lines) {

    public record Line(
            @NotBlank(message = "Campo obrigatório papaiiiiiii") String sku,
            @Positive(message = "Deve ser maior do que zero, seu mané!!!") int quantity,
            @NotNull BigDecimal unitPrice) {

    }

    public PlaceOrderCommand toCommand() {
        List<OrderLine> domainLines = lines.stream()
                .map(l -> new OrderLine(l.sku(), l.quantity(), new Money(l.unitPrice())))
                .toList();
        return new PlaceOrderCommand(customerId, domainLines);
    }
}
