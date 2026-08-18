package com.example.shop.adapter.in.messaging;

import com.example.shop.application.port.in.PlaceOrder;
import com.example.shop.domain.OrderId;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * Second driving adapter. Kafka, JSON and offsets stop here; below this class
 * the use case is the same one the REST controller calls.
 */
@Component
@ConditionalOnProperty(prefix = "shop.kafka", name = "enabled", matchIfMissing = true)
class PlaceOrderListener {

    private static final Logger log = LoggerFactory.getLogger(PlaceOrderListener.class);

    private final PlaceOrder placeOrder;
    private final ObjectMapper json;
    private final Validator validator;

    PlaceOrderListener(PlaceOrder placeOrder, ObjectMapper json, Validator validator) {
        this.placeOrder = placeOrder;
        this.json = json;
        this.validator = validator;
    }

    @KafkaListener(topics = "${shop.kafka.orders-topic}", groupId = "${spring.kafka.consumer.group-id}")
    void onMessage(@Payload String payload,
                   @Header(name = KafkaHeaders.RECEIVED_KEY, required = false) String key) {
        PlaceOrderMessage message = parse(payload);

        OrderId id = placeOrder.handle(message.toCommand());

        log.info("placed order {} from message with key {}", id, key);
    }

    private PlaceOrderMessage parse(String payload) {
        if (payload == null || payload.isBlank()) {
            throw new InvalidOrderMessageException("payload is empty");
        }

        PlaceOrderMessage message;
        try {
            message = json.readValue(payload, PlaceOrderMessage.class);
        } catch (JsonProcessingException e) {
            throw new InvalidOrderMessageException("payload is not a readable order message", e);
        }
        if (message == null) {
            throw new InvalidOrderMessageException("payload is empty");
        }

        Set<ConstraintViolation<PlaceOrderMessage>> violations = validator.validate(message);
        if (!violations.isEmpty()) {
            throw new InvalidOrderMessageException(violations.stream()
                    .map(v -> v.getPropertyPath() + " " + v.getMessage())
                    .sorted()
                    .collect(Collectors.joining(", ")));
        }
        return message;
    }
}
