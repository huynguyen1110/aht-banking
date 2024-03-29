package com.example.bank.api;

import com.example.bank.exception.StatusCode;
import com.example.bank.request.AccountCreationRequest;
import com.example.bank.response.AccountCreationResponse;
import com.example.bank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountCreationApi extends CommonApi<AccountCreationResponse, AccountCreationRequest>{

    private static final String CREATION_SUCCESSFULLY = "Create account successfully.";
    private final AccountService accountService;

    @Override
    public AccountCreationResponse execute(AccountCreationRequest request) {
        accountService.createAccount(request.getCreateAccountDTO(), request.getToken());
        return new AccountCreationResponse(StatusCode.SUCCESS.getCode(), CREATION_SUCCESSFULLY);
    }
}
