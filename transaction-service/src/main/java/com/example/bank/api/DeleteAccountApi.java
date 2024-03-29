package com.example.bank.api;

import com.example.bank.exception.StatusCode;
import com.example.bank.request.DeleteAccountRequest;
import com.example.bank.response.DeleteAccountResponse;
import com.example.bank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteAccountApi extends CommonApi<DeleteAccountResponse, DeleteAccountRequest>{

    private final AccountService accountService;

    @Override
    public DeleteAccountResponse execute(DeleteAccountRequest request) {
        accountService.deleteAccount(request.getAccountNumber());
        return new DeleteAccountResponse(StatusCode.SUCCESS.getCode(), null);
    }
}
