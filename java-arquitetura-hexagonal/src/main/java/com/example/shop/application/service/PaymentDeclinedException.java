package com.example.shop.application.service;

import com.example.shop.domain.Money;

public class PaymentDeclinedException extends RuntimeException {

    public PaymentDeclinedException(String customerId, Money amount) {
        super("payment of " + amount + " declined for customer " + customerId +
                ". Max allowed to spend is 1000.");
    }
}
