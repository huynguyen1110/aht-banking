package com.example.bank.config;

import com.example.bank.constant.Topic;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfig {

    @Bean
    public NewTopic createAccountTopic() {
        return TopicBuilder.name(Topic.CREATED_ACCOUNT.getTopic())
                .partitions(3)
                .replicas(3)
                .build();
    }
}
