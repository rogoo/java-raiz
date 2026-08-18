package br.rosa.kafka_vamos.consumer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MyTopicConsumerServiceTest {

    private final MyTopicConsumerService consumerService = new MyTopicConsumerService();

    @ParameterizedTest
    @ValueSource(strings = {"hello", "", "asdf", "666", "asdf667"})
    void consume_consumesRegularMessagesWithoutFailing(String message) {
        assertThatCode(() -> consumerService.consume(message)).doesNotThrowAnyException();
    }

    @Test
    void consume_throwsWhenMessageIsTheExactPoisonPill() {
        assertThatThrownBy(() -> consumerService.consume("asdf666"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Se enviar o texto 'asdf666' vai dar caquinha");
    }

    @Test
    void consume_throwsWhenPoisonPillIsEmbeddedInALargerMessage() {
        assertThatThrownBy(() -> consumerService.consume("prefix asdf666 suffix"))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void consume_failsFastOnNullMessage() {
        assertThatThrownBy(() -> consumerService.consume(null))
                .isInstanceOf(NullPointerException.class);
    }
}
