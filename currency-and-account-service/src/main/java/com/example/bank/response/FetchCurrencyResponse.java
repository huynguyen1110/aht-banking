package com.example.bank.response;

import com.example.bank.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class FetchCurrencyResponse extends ApiResponse{

    private Currency currency;

    public FetchCurrencyResponse(int code, String message, Currency currency) {
        super(code, message);
        this.currency = currency;
    }

    public FetchCurrencyResponse(int code, String message) {
        super(code, message);
    }


}
