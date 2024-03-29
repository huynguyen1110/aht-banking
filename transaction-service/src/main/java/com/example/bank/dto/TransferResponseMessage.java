package com.example.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TransferResponseMessage implements Serializable {

    private String sourceAccountNumber;

    private String destinationAccountNumber;

    private BigDecimal money;
}
