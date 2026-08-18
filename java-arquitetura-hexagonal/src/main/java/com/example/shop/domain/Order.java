package com.example.shop.domain;

import java.util.List;
import java.util.Objects;

/**
 * Aggregate root. Knows nothing about HTTP, JPA, or Spring - the invariants
 * live here and are enforced regardless of which adapter drives the core.
 */
public class Order {

    private final OrderId id;
    private final String customerId;
    private final List<OrderLine> lines;
    private OrderStatus status;

    private Order(OrderId id, String customerId, List<OrderLine> lines, OrderStatus status) {
        this.id = Objects.requireNonNull(id, "id");
        this.customerId = Objects.requireNonNull(customerId, "customerId");
        this.status = Objects.requireNonNull(status, "status");
        Objects.requireNonNull(lines, "lines");
        if (lines.isEmpty()) {
            throw new IllegalArgumentException("an order needs at least one line");
        }
        this.lines = List.copyOf(lines);
    }

    /** Creates a brand new order in the NEW state. */
    public static Order create(String customerId, List<OrderLine> lines) {
        return new Order(OrderId.newId(), customerId, lines, OrderStatus.NEW);
    }

    /**
     * Rebuilds an order from stored state. Only persistence adapters should call
     * this - it deliberately skips the lifecycle guards that create() implies.
     */
    public static Order restore(OrderId id, String customerId, List<OrderLine> lines, OrderStatus status) {
        return new Order(id, customerId, lines, status);
    }

    public Money total() {
        return lines.stream()
                .map(OrderLine::subtotal)
                .reduce(Money.ZERO, Money::plus);
    }

    public void confirm() {
        if (status != OrderStatus.NEW) {
            throw new IllegalStateException("cannot confirm an order in state " + status);
        }
        status = OrderStatus.CONFIRMED;
    }

    public void cancel() {
        if (status == OrderStatus.CANCELLED) {
            return;
        }
        status = OrderStatus.CANCELLED;
    }

    public OrderId id() {
        return id;
    }

    public String customerId() {
        return customerId;
    }

    public List<OrderLine> lines() {
        return lines;
    }

    public OrderStatus status() {
        return status;
    }
}
