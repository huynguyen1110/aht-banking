package com.example.bank.config.consumer;

import com.example.bank.constant.GroupId;
import com.example.bank.dto.CreatedAccountMessage;
import com.example.bank.dto.CreditResponseMessage;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CreditKafkaConsumerConfig {

    @Bean
    public KafkaTemplate<String, CreatedAccountMessage> defaultRetryTopicKafkaTemplate() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        ProducerFactory<String, CreatedAccountMessage> producerFactory = new DefaultKafkaProducerFactory<>(props);
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ConsumerFactory<String, CreditResponseMessage> creditConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, GroupId.ACCOUNT_GROUP.getGroupId());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CreditResponseMessage> creditKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, CreditResponseMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(creditConsumerFactory());
        factory.getContainerProperties().setAssignmentCommitOption(ContainerProperties.AssignmentCommitOption.ALWAYS);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.BATCH);
        factory.getContainerProperties().setEosMode(ContainerProperties.EOSMode.V2);
        return factory;
    }

}
