package com.example.bank.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UpdateAccountRequest extends ApiRequest{

    @NotBlank(message = "ACCOUNT_NOT_FOUND")
    private Long accountId;

    @NotBlank(message = "INVALID_ACCOUNT_NUMBER")
    private String accountNumber;
}
