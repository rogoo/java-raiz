package br.rosa.kafka_vamos.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MyTopicProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public MyTopicProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String message) {
        System.out.println("--> Sending message: " + message);
        kafkaTemplate.send("my-topic", message);
    }
}
