package com.tarun.springkafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import javax.annotation.PostConstruct;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.first-topic}")
    private String FIRST_TOPIC;

    @Autowired
    private KafkaAdmin kafkaAdmin;

    private NewTopic firstTopic()
    {
        return TopicBuilder.name(FIRST_TOPIC)
                .partitions(2)
                .replicas(2)
                .config(TopicConfig.RETENTION_MS_CONFIG,"1000000000")
                .build();
    }

    @PostConstruct
    public void init()
    {
        kafkaAdmin.createOrModifyTopics(firstTopic());
    }
}
