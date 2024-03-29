package com.example.bank.api;

import com.example.bank.exception.StatusCode;
import com.example.bank.request.CreateAccountRequest;
import com.example.bank.response.CreateAccountResponse;
import com.example.bank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateAccountApi extends CommonApi<CreateAccountResponse, CreateAccountRequest>{

    private final AccountService accountService;

    @Override
    public CreateAccountResponse execute(CreateAccountRequest request) {
        accountService.createAccount(request.getAccount());
        return new CreateAccountResponse(StatusCode.SUCCESS.getCode(), null);
    }
}
