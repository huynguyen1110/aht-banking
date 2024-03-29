package com.example.bank.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeleteAccountRequest extends ApiRequest{

    @NotBlank(message = "INVALID_ACCOUNT_NUMBER")
    private String accountNumber;
}
