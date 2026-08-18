package com.example.shop.config;

import com.example.shop.application.port.in.PlaceOrder;
import com.example.shop.application.port.out.OrderRepository;
import com.example.shop.application.port.out.PaymentGateway;
import com.example.shop.application.service.PlaceOrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The only place that wires ports to adapters. Because the service is built by
 * hand here, PlaceOrderService itself needs no Spring annotations at all.
 */
@Configuration
class BeanConfig {

    @Bean
    PlaceOrder placeOrder(OrderRepository orders, PaymentGateway payments) {
        return new PlaceOrderService(orders, payments);
    }
}
