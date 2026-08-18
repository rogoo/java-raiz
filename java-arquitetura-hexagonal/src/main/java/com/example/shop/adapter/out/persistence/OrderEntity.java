package com.example.shop.adapter.out.persistence;

import com.example.shop.domain.Money;
import com.example.shop.domain.Order;
import com.example.shop.domain.OrderId;
import com.example.shop.domain.OrderLine;
import com.example.shop.domain.OrderStatus;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The persistence model. Kept separate from the domain Order on purpose: JPA
 * needs a no-arg constructor and mutable fields, the domain does not.
 */
@Entity
@Table(name = "orders")
class OrderEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String customerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "order_lines", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderLineEmbeddable> lines = new ArrayList<>();

    protected OrderEntity() {
    }

    static OrderEntity fromDomain(Order order) {
        OrderEntity entity = new OrderEntity();
        entity.id = order.id().value();
        entity.customerId = order.customerId();
        entity.status = order.status();
        entity.lines = new ArrayList<>(order.lines().stream()
                .map(l -> new OrderLineEmbeddable(l.sku(), l.quantity(), l.unitPrice().amount()))
                .toList());
        return entity;
    }

    Order toDomain() {
        List<OrderLine> domainLines = lines.stream()
                .map(l -> new OrderLine(l.getSku(), l.getQuantity(), new Money(l.getUnitPrice())))
                .toList();
        return Order.restore(new OrderId(id), customerId, domainLines, status);
    }
}
