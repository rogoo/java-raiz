package com.example.shop.adapter.out.payment;

import com.example.shop.application.port.out.PaymentGateway;
import com.example.shop.domain.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Stand-in for a real provider. Swapping in Stripe means writing one new class
 * in this package and deleting this one - nothing in the core changes.
 * Declines anything over 1000 so you can exercise the failure path.
 */
@Component
class InMemoryPaymentAdapter implements PaymentGateway {

    private static final Logger log = LoggerFactory.getLogger(InMemoryPaymentAdapter.class);
    public static final Money LIMIT_PAYMENT = Money.of("1000.00");

    @Override
    public boolean charge(String customerId, Money amount) {
        boolean approved = !amount.isGreaterThan(LIMIT_PAYMENT);
        log.info("charging {} for customer {} -> {}", amount, customerId, approved ? "approved" : "declined");
        return approved;
    }
}
