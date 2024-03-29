package com.example.bank.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
@Data
@Entity
@Table(name = "dead_transfer_message")
public class DeadTransferMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "source_account_number")
    private String sourceAccountNumber;

    @Column(name = "destination_account_number")
    private String destinationAccountNumber;

    @Column(name = "money")
    private BigDecimal money;
}
