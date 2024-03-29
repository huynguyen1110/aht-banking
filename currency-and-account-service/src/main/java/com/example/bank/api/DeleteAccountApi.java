package com.example.bank.api;

import com.example.bank.exception.StatusCode;
import com.example.bank.request.DeleteAccountRequest;
import com.example.bank.response.DeleteAccountResponse;
import com.example.bank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteAccountApi extends CommonApi<DeleteAccountResponse, DeleteAccountRequest>{

    private static final String DELETE_ACCOUNT_SUCCESSFULLY = "Delete account successfully.";
    private final AccountService accountService;

    @Override
    public DeleteAccountResponse execute(DeleteAccountRequest request) {
        accountService.deleteAccount(request.getAccountNumber(), request.getToken());
        return new DeleteAccountResponse(StatusCode.SUCCESS.getCode(), DELETE_ACCOUNT_SUCCESSFULLY);
    }
}
