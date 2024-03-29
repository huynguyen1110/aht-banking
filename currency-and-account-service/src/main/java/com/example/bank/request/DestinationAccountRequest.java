package com.example.bank.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DestinationAccountRequest extends ApiRequest{

    @NotBlank(message = "INVALID_ACCOUNT_ID")
    private String destinationAccount;

    public DestinationAccountRequest(String destinationAccountId){
        this.destinationAccount = destinationAccountId;
    }
}
