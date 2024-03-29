package com.example.bank.schedule;

import com.example.bank.constant.Topic;
import com.example.bank.dto.TransferResponseMessage;
import com.example.bank.model.DeadTransferMessage;
import com.example.bank.repository.DeadTransferMessageRepository;
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
public class DeadTransferMessageSchedule implements DeadMessage {

    private final DeadTransferMessageRepository deadTransferMessageRepository;

    private final KafkaTemplate<String, TransferResponseMessage> transferKafkaTemplate;

    private final ModelMapper modelMapper;

    @Override
    @Scheduled(fixedDelay = 150000)
    public void replayMessage() {
        log.info("A poll of dead transfer message");
        List<DeadTransferMessage> deadTransferMessages = deadTransferMessageRepository.findAll();
        deadTransferMessages.forEach(deadTransferMessage -> {
            TransferResponseMessage transferResponseMessage = modelMapper.map(deadTransferMessage, TransferResponseMessage.class);
            transferKafkaTemplate.send(Topic.TRANSFER.getTopic(), transferResponseMessage).thenRun(() -> {
                deadTransferMessageRepository.deleteById(deadTransferMessage.getId());
            });
        });
    }
}
