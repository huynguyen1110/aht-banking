package com.example.bank.model;

import com.example.bank.constant.CurrencySymbol;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "currency")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Currency {

    @Id
    @Column(name = "currency_code")
    private Long currencyCode;

    @Column(length = 128, nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private CurrencySymbol symbol;
}
