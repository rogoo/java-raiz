package com.example.shop.adapter.in.messaging;

import com.example.shop.application.service.PaymentDeclinedException;
import org.apache.kafka.common.TopicPartition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

/**
 * Delivery policy, not business policy. A broker hiccup deserves a retry; a
 * malformed payload or a declined card does not, so those go straight to
 * "&lt;topic&gt;.DLT" instead of blocking the partition forever.
 */
@Configuration
@ConditionalOnProperty(prefix = "shop.kafka", name = "enabled", matchIfMissing = true)
class KafkaErrorHandlingConfig {

    private static final long RETRY_INTERVAL_MS = 1_000L;
    private static final long MAX_RETRIES = 2L;

    @Bean
    DefaultErrorHandler kafkaErrorHandler(KafkaOperations<Object, Object> template) {
        // Partition -1 lets the broker choose: the DLT need not mirror the source layout.
        var recoverer = new DeadLetterPublishingRecoverer(template,
                (record, exception) -> new TopicPartition(record.topic() + ".DLT", -1));

        var errorHandler = new DefaultErrorHandler(recoverer,
                new FixedBackOff(RETRY_INTERVAL_MS, MAX_RETRIES));
        errorHandler.addNotRetryableExceptions(
                InvalidOrderMessageException.class,
                PaymentDeclinedException.class,
                IllegalArgumentException.class);
        return errorHandler;
    }
}
