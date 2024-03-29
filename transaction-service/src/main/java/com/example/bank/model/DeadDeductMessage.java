package com.example.bank.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "dead_deduct_message")
@Data
public class DeadDeductMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "money")
    private BigDecimal money;
}
