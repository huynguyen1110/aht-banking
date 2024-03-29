package com.example.bank.event;

import com.example.bank.dto.CreditResponseMessage;
import com.example.bank.exception.AppException;
import com.example.bank.exception.ErrorCode;
import com.example.bank.model.Account;
import com.example.bank.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class CreditingHandler {

    private final AccountRepository accountRepository;

    @RetryableTopic(backoff = @Backoff(delay = 2000L, multiplier = 2))
    @KafkaListener(topics = "credit-topic", groupId = "account_group")
    @Transactional
    public void handleCrediting(ConsumerRecord<String, CreditResponseMessage> record) {
        Account account = accountRepository.findByAccountNumber(record.value().getAccountNumber());
        if(account == null) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        account.addMoney(record.value().getMoney());
        accountRepository.save(account);
        log.info("A message is handled successfully.");
    }

    @KafkaListener(topics = "credit-topic-dlt", groupId = "account_group")
    public void handleDLT(ConsumerRecord<String, CreditResponseMessage> record) {
        //Todo
        log.info("A message is sent to dlt.");
    }
}
