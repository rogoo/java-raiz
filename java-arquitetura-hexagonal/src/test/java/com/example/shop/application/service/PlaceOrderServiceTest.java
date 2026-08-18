package com.example.shop.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.shop.application.port.in.PlaceOrderCommand;
import com.example.shop.application.port.out.PaymentGateway;
import com.example.shop.domain.Money;
import com.example.shop.domain.OrderId;
import com.example.shop.domain.OrderLine;
import com.example.shop.domain.OrderStatus;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * No Spring context, no database, no HTTP - the whole use case runs in
 * microseconds because every dependency is an interface the core defined.
 */
class PlaceOrderServiceTest {

    private static final PaymentGateway ALWAYS_APPROVES = (customer, amount) -> true;
    private static final PaymentGateway ALWAYS_DECLINES = (customer, amount) -> false;

    private final InMemoryOrderRepository orders = new InMemoryOrderRepository();

    private static PlaceOrderCommand command() {
        return new PlaceOrderCommand("cust-1", List.of(
                new OrderLine("SKU-1", 2, Money.of("19.99")),
                new OrderLine("SKU-2", 1, Money.of("5.00"))));
    }

    @Test
    void confirms_and_stores_the_order_when_payment_succeeds() {
        var service = new PlaceOrderService(orders, ALWAYS_APPROVES);

        OrderId id = service.handle(command());

        var saved = orders.findById(id).orElseThrow();
        assertThat(saved.status()).isEqualTo(OrderStatus.CONFIRMED);
        assertThat(saved.total()).isEqualTo(Money.of("44.98"));
    }

    @Test
    void stores_nothing_when_payment_is_declined() {
        var service = new PlaceOrderService(orders, ALWAYS_DECLINES);

        assertThatThrownBy(() -> service.handle(command()))
                .isInstanceOf(PaymentDeclinedException.class);

        assertThat(orders.count()).isZero();
    }

    @Test
    void charges_the_computed_total_not_the_line_price() {
        var charged = new Money[1];
        var service = new PlaceOrderService(orders, (customer, amount) -> {
            charged[0] = amount;
            return true;
        });

        service.handle(command());

        assertThat(charged[0]).isEqualTo(Money.of("44.98"));
    }
}
