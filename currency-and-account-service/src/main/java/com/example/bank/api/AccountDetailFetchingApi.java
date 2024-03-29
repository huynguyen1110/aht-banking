package com.example.bank.api;

import com.example.bank.exception.StatusCode;
import com.example.bank.model.Account;
import com.example.bank.request.AccountDetailFetchingRequest;
import com.example.bank.response.AccountDetailFetchingResponse;
import com.example.bank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountDetailFetchingApi extends CommonApi<AccountDetailFetchingResponse, AccountDetailFetchingRequest>{

    private final AccountService accountService;

    @Override
    public AccountDetailFetchingResponse execute(AccountDetailFetchingRequest request) {
        Account account = accountService.getAccountDetail(request.getAccountNumber());
        return new AccountDetailFetchingResponse(StatusCode.SUCCESS.getCode(), null ,account);
    }
}
