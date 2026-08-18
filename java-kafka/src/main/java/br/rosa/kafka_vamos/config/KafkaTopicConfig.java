package br.rosa.kafka_vamos.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    public NewTopic topic() {
        return TopicBuilder.name("my-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
