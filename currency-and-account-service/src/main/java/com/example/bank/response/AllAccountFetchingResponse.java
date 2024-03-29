package com.example.bank.response;

import com.example.bank.model.Account;

import java.util.List;

public class AllAccountFetchingResponse extends ApiResponse {

    public List<Account> accounts;

    public AllAccountFetchingResponse(int code, String message) {
        super(code, message);
    }

    public AllAccountFetchingResponse(int code, String message, List<Account> accounts) {
        super(code, message);
        this.accounts = accounts;
    }
}
