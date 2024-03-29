package com.example.bank.config;

import com.example.bank.constant.Topic;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfig {

    @Bean
    public NewTopic creditTopic() {
        return TopicBuilder.name(Topic.CREDIT.getTopic())
                .partitions(3)
                .replicas(3)
                .build();
    }

    @Bean
    public NewTopic deductTopic() {
        return TopicBuilder.name(Topic.DEDUCT.getTopic())
                .partitions(3)
                .replicas(3)
                .build();
    }

    @Bean
    public NewTopic transferTopic() {
        return TopicBuilder.name(Topic.TRANSFER.getTopic())
                .partitions(3)
                .replicas(3)
                .build();
    }
}
