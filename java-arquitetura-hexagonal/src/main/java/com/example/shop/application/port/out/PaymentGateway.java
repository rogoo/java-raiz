package com.example.shop.application.port.out;

import com.example.shop.domain.Money;

/** Driven port: taking money, with no hint of which provider does it. */
public interface PaymentGateway {
    boolean charge(String customerId, Money amount);
}
