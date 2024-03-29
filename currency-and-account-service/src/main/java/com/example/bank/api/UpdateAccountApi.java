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

    private static final String UPDATE_ACCOUNT_SUCCESSFULLY = "Update account successfully.";
    private final AccountService accountService;

    @Override
    public UpdateAccountResponse execute(UpdateAccountRequest request) {
        accountService.updateAccount(request.getAccountId(), request.getNewAccountNumber(), request.getToken());
        return new UpdateAccountResponse(StatusCode.SUCCESS.getCode(), UPDATE_ACCOUNT_SUCCESSFULLY);
    }
}
