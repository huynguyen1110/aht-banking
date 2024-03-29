package com.example.bank.model;

import com.example.bank.constant.CurrencySymbol;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "currency")
@Data
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "currency_code")
    private Long currencyCode;

    @Column(length = 128, nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private CurrencySymbol symbol;
}
