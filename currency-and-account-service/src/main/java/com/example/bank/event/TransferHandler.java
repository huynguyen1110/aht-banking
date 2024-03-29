package com.example.bank.event;

import com.example.bank.dto.TransferResponseMessage;
import com.example.bank.exception.AppException;
import com.example.bank.exception.ErrorCode;
import com.example.bank.model.Account;
import com.example.bank.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class TransferHandler {

    private final AccountRepository accountRepository;

    @RetryableTopic(backoff = @Backoff(delay = 2000L, multiplier = 2))
    @KafkaListener(topics = "transfer-topic", groupId = "account-group")
    @Transactional
    public void handleTransfer(ConsumerRecord<String, TransferResponseMessage> record) {
        Account sourceAccount = accountRepository.findByAccountNumber(record.value().getSourceAccountNumber());
        Account destinationAccount = accountRepository.findByAccountNumber(record.value().getDestinationAccountNumber());
        if(sourceAccount == null || destinationAccount == null) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        sourceAccount.subtractMoney(record.value().getMoney());
        destinationAccount.addMoney(record.value().getMoney());
        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);
    }
}
