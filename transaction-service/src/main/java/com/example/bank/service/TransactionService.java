package com.example.bank.service;

import com.example.bank.constant.Topic;
import com.example.bank.constant.TransactionType;
import com.example.bank.dto.CreditResponseMessage;
import com.example.bank.dto.DeductResponseMessage;
import com.example.bank.dto.TransferResponseMessage;
import com.example.bank.exception.AppException;
import com.example.bank.exception.ErrorCode;
import com.example.bank.model.*;
import com.example.bank.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final KafkaTemplate<String, CreditResponseMessage> creditKafkaTemplate;
    private final KafkaTemplate<String, DeductResponseMessage> deductKafkaTemplate;
    private final KafkaTemplate<String, TransferResponseMessage> transferKafkaTemplate;
    private final ModelMapper modelMapper;
    private final DeadCreditMessageRepository deadCreditMessageRepository;
    private final DeadDebitMessageRepository deadDebitMessageRepository;
    private final DeadTransferMessageRepository deadTransferMessageRepository;

    private void subtractMoneyInAccount(String accountNumber, BigDecimal money) {
        Account account = accountService.findAccount(accountNumber);
        accountService.isBalanceSufficient(account, money);
        account.subtractMoney(money);
        accountRepository.save(account);
    }

    private void addMoneyToAccount(String accountNumber, BigDecimal money) {
        Account account = accountService.findAccount(accountNumber);
        account.addMoney(money);
        accountRepository.save(account);
    }

    private void validateUserId(String sourceAccountNumber,Long userId) {
        if(!accountRepository.existsAccountByAccountNumberEqualsAndUserId(sourceAccountNumber, userId)) {
            throw new AppException(ErrorCode.ERROR_AUTHENTICATE_ACCOUNT_OWNER);
        }
    }

    public Page<Transaction> getAllTransactionOfAnUser(Long userId, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("transaction_date").descending());
        return transactionRepository.getAllTransactionOfAnUser(userId, pageable);
    }

    @Transactional
    public void credit(String destinationAccountNumber, BigDecimal money) {
        this.addMoneyToAccount(destinationAccountNumber, money);
        Transaction transaction = Transaction.builder()
                .transactionDate(Date.valueOf(LocalDate.now()))
                .transactionType(TransactionType.CREDIT)
                .destinationAccountNumber(destinationAccountNumber)
                .amount(money)
                .build();
        transactionRepository.save(transaction);

        CreditResponseMessage creditResponseMessage = new CreditResponseMessage(destinationAccountNumber, money);
        creditKafkaTemplate.send(Topic.CREDIT.getTopic(), creditResponseMessage)
                .exceptionally(throwable -> {
                    DeadCreditMessage deadCreditMessage = modelMapper.map(creditResponseMessage, DeadCreditMessage.class);
                    deadCreditMessageRepository.save(deadCreditMessage);
                    return null;
                });
    }

    @Transactional
    public void deduct(String sourceAccountNumber,
                       BigDecimal money,
                       Long userId) {
        this.validateUserId(sourceAccountNumber, userId);
        subtractMoneyInAccount(sourceAccountNumber, money);
        Transaction transaction = Transaction.builder()
                .transactionDate(Date.valueOf(LocalDate.now()))
                .transactionType(TransactionType.DEDUCT)
                .sourceAccountNumber(sourceAccountNumber)
                .amount(money)
                .build();
        transactionRepository.save(transaction);

        DeductResponseMessage deductResponseMessage = new DeductResponseMessage(sourceAccountNumber, money);
        deductKafkaTemplate.send(Topic.DEDUCT.getTopic(), deductResponseMessage)
                .exceptionally(throwable -> {
                    DeadDeductMessage deadDeductMessage = modelMapper.map(deductResponseMessage, DeadDeductMessage.class);
                    deadDebitMessageRepository.save(deadDeductMessage);
                    return null;
                });
    }

    @Transactional
    public void transfer(String sourceAccountNumber,
                         String destinationAccountNumber,
                         BigDecimal money,
                         Long userId) {
        this.validateUserId(sourceAccountNumber, userId);
        subtractMoneyInAccount(sourceAccountNumber, money);
        addMoneyToAccount(destinationAccountNumber, money);
        Transaction transaction = Transaction.builder()
                .transactionDate(Date.valueOf(LocalDate.now()))
                .transactionType(TransactionType.TRANSFER)
                .sourceAccountNumber(sourceAccountNumber)
                .destinationAccountNumber(destinationAccountNumber)
                .amount(money)
                .build();
        transactionRepository.save(transaction);

        TransferResponseMessage transferResponseMessage = new TransferResponseMessage(sourceAccountNumber, destinationAccountNumber, money);
        transferKafkaTemplate.send(Topic.TRANSFER.getTopic(), transferResponseMessage)
                .exceptionally(throwable -> {
                    DeadTransferMessage deadTransferMessage = modelMapper.map(transferResponseMessage, DeadTransferMessage.class);
                    deadTransferMessageRepository.save(deadTransferMessage);
                    return null;
                });
    }
}
