package com.example.shop.adapter.out.persistence;

import com.example.shop.application.port.out.OrderRepository;
import com.example.shop.domain.Order;
import com.example.shop.domain.OrderId;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/** Driven adapter: implements an out-port and maps between the two models. */
@Component
class JpaOrderRepositoryAdapter implements OrderRepository {

    private final SpringDataOrderRepository jpa;

    JpaOrderRepositoryAdapter(SpringDataOrderRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    @Transactional
    public void save(Order order) {
        jpa.save(OrderEntity.fromDomain(order));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Order> findById(OrderId id) {
        return jpa.findById(id.value()).map(OrderEntity::toDomain);
    }
}
