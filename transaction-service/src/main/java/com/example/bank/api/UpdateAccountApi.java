package com.example.bank.api;

import com.example.bank.exception.StatusCode;
import com.example.bank.request.UpdateAccountRequest;
import com.example.bank.response.UpdateAccountResponse;
import com.example.bank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateAccountApi extends CommonApi<UpdateAccountResponse, UpdateAccountRequest>{

    private final AccountService accountService;

    @Override
    public UpdateAccountResponse execute(UpdateAccountRequest request) {
        accountService.updateAccount(request.getAccountId(), request.getAccountNumber());
        return new UpdateAccountResponse(StatusCode.SUCCESS.getCode(), null);
    }
}
