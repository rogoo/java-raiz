package br.rosa.kafka_vamos.producer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class MyTopicProducerServiceTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private MyTopicProducerService producerService;

    @Test
    void send_sendsMessageToMyTopic() {
        producerService.send("hello");

        ArgumentCaptor<String> topic = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> payload = ArgumentCaptor.forClass(String.class);
        verify(kafkaTemplate).send(topic.capture(), payload.capture());

        assertThat(topic.getValue()).isEqualTo("my-topic");
        assertThat(payload.getValue()).isEqualTo("hello");
        verifyNoMoreInteractions(kafkaTemplate);
    }

    @Test
    void send_sendsEachMessageSeparately() {
        producerService.send("first");
        producerService.send("second");

        verify(kafkaTemplate).send("my-topic", "first");
        verify(kafkaTemplate).send("my-topic", "second");
        verifyNoMoreInteractions(kafkaTemplate);
    }

    @Test
    void send_sendsEmptyMessageAsIs() {
        producerService.send("");

        verify(kafkaTemplate).send("my-topic", "");
    }
}
