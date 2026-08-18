package com.example.shop.adapter.in.messaging;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.shop.application.port.in.PlaceOrderCommand;
import com.example.shop.domain.Money;
import com.example.shop.domain.OrderId;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * No broker and no Spring context: the listener is just a class that maps JSON
 * onto the in-port, so it can be tested like one.
 */
class PlaceOrderListenerTest {

    private static final String VALID = """
            {
              "customerId": "cust-1",
              "lines": [
                { "sku": "SKU-1", "quantity": 2, "unitPrice": 19.99 }
              ]
            }
            """;

    private static final ValidatorFactory VALIDATOR_FACTORY = Validation.buildDefaultValidatorFactory();
    private static final Validator VALIDATOR = VALIDATOR_FACTORY.getValidator();

    private final List<PlaceOrderCommand> received = new ArrayList<>();
    private final PlaceOrderListener listener = new PlaceOrderListener(
            command -> {
                received.add(command);
                return OrderId.newId();
            },
            new ObjectMapper(),
            VALIDATOR);

    @Test
    void maps_the_message_onto_the_in_port() {
        listener.onMessage(VALID, "cust-1");

        assertThat(received).hasSize(1);
        PlaceOrderCommand command = received.getFirst();
        assertThat(command.customerId()).isEqualTo("cust-1");
        assertThat(command.lines()).singleElement().satisfies(line -> {
            assertThat(line.sku()).isEqualTo("SKU-1");
            assertThat(line.quantity()).isEqualTo(2);
            assertThat(line.unitPrice()).isEqualTo(Money.of("19.99"));
        });
    }

    @Test
    void rejects_unreadable_json_without_touching_the_core() {
        assertThatThrownBy(() -> listener.onMessage("{ not json", null))
                .isInstanceOf(InvalidOrderMessageException.class);

        assertThat(received).isEmpty();
    }

    @Test
    void rejects_a_payload_that_breaks_the_wire_contract() {
        String noLines = """
                { "customerId": "cust-1", "lines": [] }
                """;

        assertThatThrownBy(() -> listener.onMessage(noLines, "cust-1"))
                .isInstanceOf(InvalidOrderMessageException.class)
                .hasMessageContaining("lines");

        assertThat(received).isEmpty();
    }

    @Test
    void rejects_an_empty_payload() {
        assertThatThrownBy(() -> listener.onMessage(null, "cust-1"))
                .isInstanceOf(InvalidOrderMessageException.class);
    }
}
