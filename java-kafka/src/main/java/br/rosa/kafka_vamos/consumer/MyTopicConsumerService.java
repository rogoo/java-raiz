package br.rosa.kafka_vamos.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MyTopicConsumerService {

    @KafkaListener(topics = "my-topic", groupId = "vamos-consumer")
    public void consume(String message) {
        if (message.contains("asdf666")) {
            throw new RuntimeException("Se enviar o texto \'asdf666\' vai dar caquinha");
        }

        System.out.println("--> Consumed message: " + message);
    }
}
