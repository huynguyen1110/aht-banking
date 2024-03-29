package com.example.bank.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.jwt.Jwt;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class DrawMoneyRequest extends ApiRequest{

    private String sourceAccountNumber;

    private BigDecimal money;

    private Jwt jwt;
}
