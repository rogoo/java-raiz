package com.example.shop.application.service;

import com.example.shop.application.port.out.OrderRepository;
import com.example.shop.domain.Order;
import com.example.shop.domain.OrderId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/** A fake out-port. Twelve lines, and it replaces an entire database. */
class InMemoryOrderRepository implements OrderRepository {

    private final Map<OrderId, Order> stored = new HashMap<>();

    @Override
    public void save(Order order) {
        stored.put(order.id(), order);
    }

    @Override
    public Optional<Order> findById(OrderId id) {
        return Optional.ofNullable(stored.get(id));
    }

    int count() {
        return stored.size();
    }
}
