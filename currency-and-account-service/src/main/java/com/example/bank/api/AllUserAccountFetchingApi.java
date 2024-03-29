package com.example.bank.api;

import com.example.bank.exception.StatusCode;
import com.example.bank.model.Account;
import com.example.bank.request.AllAccountFetchingRequest;
import com.example.bank.response.AllAccountFetchingResponse;
import com.example.bank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AllUserAccountFetchingApi extends CommonApi<AllAccountFetchingResponse, AllAccountFetchingRequest>{

    private final AccountService accountService;

    @Override
    public AllAccountFetchingResponse execute(AllAccountFetchingRequest request) {
        Long userId = 3L;
        List<Account> accounts = accountService.getAllUserAccounts(userId);
        return new AllAccountFetchingResponse(StatusCode.SUCCESS.getCode(), null, accounts);
    }
}
