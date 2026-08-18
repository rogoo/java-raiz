package com.example.shop.application.port.out;

import com.example.shop.domain.Order;
import com.example.shop.domain.OrderId;
import java.util.Optional;

/** Driven port: storage, expressed in the core's own vocabulary. */
public interface OrderRepository {
    void save(Order order);
    Optional<Order> findById(OrderId id);
}
