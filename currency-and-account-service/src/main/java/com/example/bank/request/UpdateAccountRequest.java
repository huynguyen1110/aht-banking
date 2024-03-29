package com.example.bank.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UpdateAccountRequest extends ApiRequest{

    @NotNull(message = "ACCOUNT_NOT_FOUND")
    private Long accountId;

    @NotBlank(message = "INVALID_ACCOUNT_NUMBER")
    private String newAccountNumber;

    public UpdateAccountRequest(Long accountId, String newAccountNumber, String token) {
        this.accountId = accountId;
        this.newAccountNumber = newAccountNumber;
        this.token = token;
    }
}
