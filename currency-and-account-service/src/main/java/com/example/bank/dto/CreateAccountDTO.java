package com.example.bank.dto;

import com.example.bank.constant.AccountType;
import com.example.bank.validation.AccountNumberValidation;
import com.example.bank.validation.AccountTypeValidation;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateAccountDTO {

    @NotNull(message = "User Id can not be blank.")
    private Long userId;

    @NotNull(message = "Account Type can not be blank.")
    @AccountTypeValidation(regexp = "SAVINGS|CHECKING|MONEY_MARKET|CERTIFICATE_OF_DEPOSIT")
    private AccountType accountType;

    @PositiveOrZero(message = "The balance must be greater than or equal 0.")
    private BigDecimal balance;

    @AccountNumberValidation
    @NotBlank(message = "Account Number can not be blank.")
    private String accountNumber;
}
