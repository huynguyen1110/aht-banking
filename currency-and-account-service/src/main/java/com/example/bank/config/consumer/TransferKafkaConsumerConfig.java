package com.example.bank.config.consumer;

import com.example.bank.constant.GroupId;
import com.example.bank.dto.DeductResponseMessage;
import com.example.bank.dto.TransferResponseMessage;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TransferKafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, TransferResponseMessage> transferConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, GroupId.ACCOUNT_GROUP.getGroupId());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TransferResponseMessage> deductKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, TransferResponseMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(transferConsumerFactory());
        factory.getContainerProperties().setAssignmentCommitOption(ContainerProperties.AssignmentCommitOption.ALWAYS);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);
        factory.getContainerProperties().setEosMode(ContainerProperties.EOSMode.V2);
        return factory;
    }
}
