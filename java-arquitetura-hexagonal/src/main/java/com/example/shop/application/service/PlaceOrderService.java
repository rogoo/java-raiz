package com.example.shop.application.service;

import com.example.shop.application.port.in.PlaceOrder;
import com.example.shop.application.port.in.PlaceOrderCommand;
import com.example.shop.application.port.out.OrderRepository;
import com.example.shop.application.port.out.PaymentGateway;
import com.example.shop.domain.Order;
import com.example.shop.domain.OrderId;

/**
 * Orchestrates one use case. Holds no business rules of its own - those stay in
 * the domain - and knows the outside world only through the two out-ports below.
 */
public class PlaceOrderService implements PlaceOrder {

    private final OrderRepository orderRepository;
    private final PaymentGateway paymentGateway;

    public PlaceOrderService(OrderRepository orderRepository, PaymentGateway paymentGateway) {
        this.orderRepository = orderRepository;
        this.paymentGateway = paymentGateway;
    }

    @Override
    public OrderId handle(PlaceOrderCommand command) {
        Order order = Order.create(command.customerId(), command.lines());

        boolean paid = paymentGateway.charge(order.customerId(), order.total());
        if (!paid) {
            throw new PaymentDeclinedException(order.customerId(), order.total());
        }

        order.confirm();
        orderRepository.save(order);
        return order.id();
    }
}
