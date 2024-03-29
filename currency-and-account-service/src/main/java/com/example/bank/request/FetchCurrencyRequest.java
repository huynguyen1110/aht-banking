package com.example.bank.request;

import com.example.bank.constant.CurrencySymbol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class FetchCurrencyRequest extends ApiRequest{

    private CurrencySymbol currencySymbol;
}
