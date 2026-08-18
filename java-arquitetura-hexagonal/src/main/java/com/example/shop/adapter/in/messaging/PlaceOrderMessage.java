package com.example.shop.adapter.in.messaging;

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
 * Wire format of an incoming Kafka message. Deliberately separate from the web
 * adapter's CreateOrderRequest: the two channels are free to evolve apart.
 */
public record PlaceOrderMessage(
        @NotBlank String customerId,
        @NotEmpty @Valid List<Line> lines) {

    public record Line(
            @NotBlank String sku,
            @Positive int quantity,
            @NotNull BigDecimal unitPrice) {
    }

    public PlaceOrderCommand toCommand() {
        List<OrderLine> domainLines = lines.stream()
                .map(l -> new OrderLine(l.sku(), l.quantity(), new Money(l.unitPrice())))
                .toList();
        return new PlaceOrderCommand(customerId, domainLines);
    }
}
