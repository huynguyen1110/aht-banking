package com.example.bank.request;

import com.example.bank.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UpdateCurrencyRequest extends ApiRequest{

    private Currency currency;
}
