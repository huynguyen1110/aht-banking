package com.example.bank.model;

import com.example.bank.constant.AccountType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "account_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(name = "create_at", nullable = false)
    private Date createAt;

    @Column(name = "account_number", nullable = false, unique = true, columnDefinition = "CHAR(10)")
    private String accountNumber;

    public void subtractMoney(BigDecimal money) {
        this.balance = this.balance.subtract(money);
    }

    public void addMoney(BigDecimal money) {
        this.balance = this.balance.add(money);
    }

}
