package com.example.bank.controller;

import com.example.bank.api.*;
import com.example.bank.request.*;
import com.example.bank.response.*;
import com.example.bank.validation.AccountNumberValidation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
@Validated
public class TransactionController {

    private final DrawMoneyApi drawMoneyApi;
    private final DepositApi depositApi;
    private final TransferApi transferApi;
    private final TransactionApi transactionApi;

    @PostMapping("/draw")
    public DrawMoneyResponse drawMoney(
            @RequestParam @NotBlank(message = "Please fill the account number.") @AccountNumberValidation String sourceAccountNumber,
            @RequestParam @Positive(message = "Money can not be less than 0.") BigDecimal money,
            @AuthenticationPrincipal Jwt jwt) {
        DrawMoneyRequest drawMoneyRequest = new DrawMoneyRequest(sourceAccountNumber, money, jwt);
        return drawMoneyApi.execute(drawMoneyRequest);
    }

    @PostMapping("/deposit")
    public DepositResponse deposit(
            @RequestParam @NotBlank(message = "Please fill the account number.") @AccountNumberValidation String destinationAccountNumber,
            @RequestParam @Positive(message = "Money can not be less than 0.") BigDecimal money) {
        DepositRequest depositRequest = new DepositRequest(destinationAccountNumber, money);
        return depositApi.execute(depositRequest);
    }

    @PostMapping("/transfer")
    public TransferResponse transfer(
            @RequestParam @NotBlank(message = "Please fill the account number.") @AccountNumberValidation String sourceAccountNumber,
            @RequestParam @NotBlank(message = "Please fill the account number.") @AccountNumberValidation String destinationAccountNumber,
            @RequestParam @Positive(message = "Money can not be less than 0.") BigDecimal money,
            @AuthenticationPrincipal @NotNull(message = "Error in authenticate account owner.") Jwt jwt){
        TransferRequest transferRequest = new TransferRequest(sourceAccountNumber,
                destinationAccountNumber,
                money,
                jwt);
        return transferApi.execute(transferRequest);
    }

    @GetMapping("/get-transaction/{pageNumber}")
    public TransactionResponse getTransaction(
            @PathVariable Integer pageNumber,
            @AuthenticationPrincipal @NotNull(message = "Error in authenticate account owner.") Jwt jwt) {
        TransactionRequest transactionRequest = new TransactionRequest(jwt, pageNumber);
        return transactionApi.execute(transactionRequest);
    }
}
