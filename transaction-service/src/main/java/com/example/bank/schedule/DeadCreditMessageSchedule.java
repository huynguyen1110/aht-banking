package com.example.bank.schedule;

import com.example.bank.constant.Topic;
import com.example.bank.dto.CreditResponseMessage;
import com.example.bank.model.DeadCreditMessage;
import com.example.bank.repository.DeadCreditMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DeadCreditMessageSchedule implements DeadMessage {

    private final DeadCreditMessageRepository deadCreditMessageRepository;

    private final KafkaTemplate<String, CreditResponseMessage> creditKafkaTemplate;

    private final ModelMapper modelMapper;

    @Scheduled(fixedDelay = 400000)
    @Override
    public void replayMessage() {
        log.info("A poll of dead credit message.");
        List<DeadCreditMessage> deadCreditMessages = deadCreditMessageRepository.findAll();
        deadCreditMessages.forEach(deadCreditMessage -> {
            CreditResponseMessage creditResponseMessage = modelMapper.map(deadCreditMessage, CreditResponseMessage.class);
            creditKafkaTemplate.send(Topic.CREDIT.getTopic(), creditResponseMessage).thenRun(() -> {
                deadCreditMessageRepository.deleteById(deadCreditMessage.getId());
            });
        });
    }
}
