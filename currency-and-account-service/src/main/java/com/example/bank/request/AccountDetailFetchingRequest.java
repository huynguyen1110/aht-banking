package com.example.bank.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountDetailFetchingRequest extends ApiRequest {

    @NotBlank(message = "INVALID_ACCOUNT_NUMBER")
    private String accountNumber;
}
