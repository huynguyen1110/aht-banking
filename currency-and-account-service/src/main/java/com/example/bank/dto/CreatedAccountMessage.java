package com.example.bank.dto;

import com.example.bank.constant.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatedAccountMessage {

    private Long accountId;

    private AccountType accountType;

    private BigDecimal balance;

    private String accountNumber;
}
