package com.example.bank.service;

import com.example.bank.dto.CreateAccountDTO;
import com.example.bank.exception.AppException;
import com.example.bank.exception.ErrorCode;
import com.example.bank.model.Account;
import com.example.bank.repository.AccountRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private static final String EXIST_USER_URL = "http://auth-service/users/exist-user";
    private static final String SYN_CREATE_ACCOUNT_TO_TRANSACTION_SERVICE_URL = "http://transaction-service/account/create";
    private static final String SYN_DELETE_ACCOUNT_TO_TRANSACTION_SERVICE_URL = "http://transaction-service/account/delete";
    private static final String SYN_UPDATE_ACCOUNT_TO_TRANSACTION_SERVICE_URL = "http://transaction-service/account/update";
    private static final BigDecimal INITIAL_BALANCE_VALUE = new BigDecimal(0);
    private final AccountRepository accountRepository;
    private final RestTemplate restTemplate;


    private boolean existUser(Long userId, String bearerToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(bearerToken);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(EXIST_USER_URL)
                .queryParam("userId", userId);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        Boolean isExistedUser = restTemplate.exchange(builder.toUriString(),
                HttpMethod.GET,
                entity,
                Boolean.class)
                .getBody();
        return Boolean.TRUE.equals(isExistedUser);
    }

    private void synCreateAccountToTransactionService(Account account, String bearerToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(bearerToken);
        HttpEntity<Account> entity = new HttpEntity<>(account, headers);
        restTemplate.exchange(SYN_CREATE_ACCOUNT_TO_TRANSACTION_SERVICE_URL,
                HttpMethod.POST,
                entity,
                Void.class);
    }

    private void synDeleteAccountToTransactionService(String accountNumber, String bearerToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(bearerToken);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(SYN_DELETE_ACCOUNT_TO_TRANSACTION_SERVICE_URL)
                .queryParam("accountNumber", accountNumber);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        restTemplate.exchange(builder.toUriString(),
                HttpMethod.DELETE,
                entity,
                Void.class);
    }

    private void synUpdateAccountToTransactionService(Long accountId, String accountNumber, String bearerToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(bearerToken);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(SYN_UPDATE_ACCOUNT_TO_TRANSACTION_SERVICE_URL)
                .queryParam("accountNumber", accountNumber)
                .queryParam("accountId", accountId);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        restTemplate.exchange(builder.toUriString(),
                HttpMethod.PUT,
                entity,
                Void.class);
    }

    private void validateAccountBeforeCreation(Account account, String bearerToken) {
        if (!existUser(account.getUserId(), bearerToken)) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        if (accountRepository.existsAccountByAccountNumberEquals(account.getAccountNumber())) {
            throw new AppException(ErrorCode.ACCOUNT_ALREADY_EXISTED);
        }
        if (account.getBalance() == null) {
            account.setBalance(INITIAL_BALANCE_VALUE);
        }
    }

    public Account createAccountFallBack(CreateAccountDTO createAccountDTO, String bearerToken, Throwable throwable) {
        throw new AppException(ErrorCode.REQUEST_TIMEOUT);
    }

    @Transactional
    @CircuitBreaker(name = "CREATE_ACCOUNT_BREAKER", fallbackMethod = "createAccountFallBack")
    public Account createAccount(CreateAccountDTO createAccountDTO, String bearerToken) {
        Account account = Account.builder()
                .accountType(createAccountDTO.getAccountType())
                .accountNumber(createAccountDTO.getAccountNumber())
                .userId(createAccountDTO.getUserId())
                .balance(createAccountDTO.getBalance())
                .createAt(Date.valueOf(LocalDate.now()))
                .build();
        this.validateAccountBeforeCreation(account, bearerToken);
        Account createdAccount = accountRepository.save(account);
        this.synCreateAccountToTransactionService(createdAccount, bearerToken);

        return createdAccount;
    }

    @Transactional
    @CircuitBreaker(name = "DELETE_ACCOUNT_BREAKER", fallbackMethod = "deleteAccountFallBack")
    public void deleteAccount(String accountNumber, String bearerToken) {
        if(!accountRepository.existsAccountByAccountNumberEquals(accountNumber)) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        accountRepository.deleteAccountByAccountNumber(accountNumber);
        this.synDeleteAccountToTransactionService(accountNumber, bearerToken);
    }

    public Account deleteAccountFallBack(String accountNumber,
                                         String bearerToken,
                                         Throwable throwable) {
        throw new AppException(ErrorCode.REQUEST_TIMEOUT);
    }

    public Account getAccountDetail(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        return account;
    }

    @Transactional
    @CircuitBreaker(name = "UPDATE_ACCOUNT_BREAKER", fallbackMethod = "updateAccountFallBack")
    public void updateAccount(Long accountId, String newAccountNumber, String bearerToken) {
        if(accountRepository.existsAccountByAccountNumberEquals(newAccountNumber)) {
            throw new AppException(ErrorCode.ACCOUNT_ALREADY_EXISTED);
        }
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        account.setAccountNumber(newAccountNumber);
        accountRepository.save(account);
        this.synUpdateAccountToTransactionService(accountId, newAccountNumber, bearerToken);
    }

    public Account updateAccountFallBack(Long accountId,
                                         String newAccountNumber,
                                         String bearerToken,
                                         Throwable throwable) {
        throw new AppException(ErrorCode.REQUEST_TIMEOUT);
    }

    public List<Account> getAllUserAccounts(Long userId) {
        if(!accountRepository.existsAccountsByUserId(userId)) {
            throw new AppException(ErrorCode.USER_ACCOUNT_NOT_FOUND);
        }else {
            return accountRepository.getAccountByUserId(userId);
        }

    }
}
