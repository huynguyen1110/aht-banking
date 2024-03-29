package com.example.bank.response;

import com.example.bank.model.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDetailFetchingResponse extends ApiResponse{

    private Account account;

    public AccountDetailFetchingResponse(int code, String message, Account account) {
        super(code, message);
        this.account = account;
    }

    public AccountDetailFetchingResponse(int code, String message) {
        super(code, message);
    }
}
