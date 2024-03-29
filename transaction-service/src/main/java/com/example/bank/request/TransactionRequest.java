package com.example.bank.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.oauth2.jwt.Jwt;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransactionRequest extends ApiRequest{

    private Jwt jwt;

    private Integer pageNumber;
}
