package com.example.bank.request;

import com.example.bank.model.Account;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateAccountRequest extends ApiRequest{

    @NotNull(message = "INVALID_ACCOUNT_NUMBER")
    private Account account;
}
