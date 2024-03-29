package com.example.bank.event;

import com.example.bank.dto.DeductResponseMessage;
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
public class DeductHandler {

    private final AccountRepository accountRepository;

    @RetryableTopic(backoff = @Backoff(delay = 2000L, multiplier = 2))
    @KafkaListener(topics = "deduct-topic", groupId = "account-group")
    @Transactional
    public void handleDeducting(ConsumerRecord<String, DeductResponseMessage> record) {
        Account account = accountRepository.findByAccountNumber(record.value().getAccountNumber());
        if(account == null) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        account.subtractMoney(record.value().getMoney());
        accountRepository.save(account);
    }
}
