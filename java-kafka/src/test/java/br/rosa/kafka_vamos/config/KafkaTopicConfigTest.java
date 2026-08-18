package br.rosa.kafka_vamos.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class KafkaTopicConfigTest {

    private final KafkaTopicConfig kafkaTopicConfig = new KafkaTopicConfig();

    @Test
    void topic_RighttttttConfig() {
        NewTopic topic = kafkaTopicConfig.topic();

        assertThat(topic.name()).isEqualTo("my-topic");
        assertThat(topic.numPartitions()).isEqualTo(3);
        assertThat(topic.replicationFactor()).isEqualTo((short) 1);
    }
}
