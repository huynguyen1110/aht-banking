package com.example.bank.request;

import com.example.bank.dto.CreateAccountDTO;
import com.example.bank.model.Account;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class AccountCreationRequest extends ApiRequest{

    @NotNull(message = "ACCOUNT_NOT_FILLED")
    private CreateAccountDTO createAccountDTO;

    public AccountCreationRequest(CreateAccountDTO createAccountDTO, String token) {
        this.createAccountDTO = createAccountDTO;
        this.token = token;
    }
}
