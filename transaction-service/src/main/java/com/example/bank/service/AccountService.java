package com.example.bank.service;

import com.example.bank.exception.AppException;
import com.example.bank.exception.ErrorCode;
import com.example.bank.model.Account;
import com.example.bank.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public void isBalanceSufficient(Account account, BigDecimal money) {
        if (account.getBalance().compareTo(money) < 0) {
            throw new AppException(ErrorCode.NOT_ENOUGH_MONEY_IN_ACCOUNT);
        }
    }

    public Account findAccount(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        return account;
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    @Transactional
    public void deleteAccount(String accountNumber) {
        accountRepository.deleteByAccountNumberEquals(accountNumber);
    }

    public void updateAccount(Long accountId, String accountNumber) {
        accountRepository.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND))
                .setAccountNumber(accountNumber);
    }
}
