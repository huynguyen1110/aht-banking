package com.example.bank.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TransferResponseMessage implements Serializable{

    private String sourceAccountNumber;

    private String destinationAccountNumber;

    private BigDecimal money;
}
