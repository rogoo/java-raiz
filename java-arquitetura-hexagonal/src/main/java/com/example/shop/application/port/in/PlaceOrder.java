package com.example.shop.application.port.in;

import com.example.shop.domain.OrderId;

/** Driving port: what the outside world may ask the core to do. */
public interface PlaceOrder {
    OrderId handle(PlaceOrderCommand command);
}
