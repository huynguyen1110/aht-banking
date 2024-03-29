package com.example.bank.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class DeleteAccountRequest extends ApiRequest{

    @NotBlank(message = "INVALID_ACCOUNT_NUMBER")
    private String accountNumber;

    public DeleteAccountRequest(String accountNumber, String token) {
        this.accountNumber = accountNumber;
        this.token = token;
    }
}
