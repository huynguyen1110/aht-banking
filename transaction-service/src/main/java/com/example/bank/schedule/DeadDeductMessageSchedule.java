package com.example.bank.schedule;

import com.example.bank.constant.Topic;
import com.example.bank.dto.DeductResponseMessage;
import com.example.bank.model.DeadDeductMessage;
import com.example.bank.repository.DeadDebitMessageRepository;
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
public class DeadDeductMessageSchedule implements DeadMessage {

    private final DeadDebitMessageRepository deadDebitMessageRepository;

    private final KafkaTemplate<String, DeductResponseMessage> debitKafkaTemplate;

    private final ModelMapper modelMapper;

    @Scheduled(fixedDelay = 300000)
    @Override
    public void replayMessage() {
        log.info("A poll of dead debit message");
        List<DeadDeductMessage> deadDeductMessages = deadDebitMessageRepository.findAll();
        deadDeductMessages.forEach(deadDeductMessage -> {
            DeductResponseMessage deductResponseMessage = modelMapper.map(deadDeductMessage, DeductResponseMessage.class);
            debitKafkaTemplate.send(Topic.DEDUCT.getTopic(), deductResponseMessage).thenRun(() -> {
                deadDebitMessageRepository.deleteById(deadDeductMessage.getId());
            });
        });
    }
}
