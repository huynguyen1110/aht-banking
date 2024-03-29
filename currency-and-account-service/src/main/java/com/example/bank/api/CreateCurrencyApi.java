package com.example.bank.api;

import com.example.bank.exception.StatusCode;
import com.example.bank.request.CreateCurrencyRequest;
import com.example.bank.response.CreateCurrencyResponse;
import com.example.bank.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateCurrencyApi extends CommonApi<CreateCurrencyResponse, CreateCurrencyRequest>{

    private static final String CREATE_CURRENCY_SUCCESSFULLY = "Create currency successfully.";
    private final CurrencyService currencyService;

    @Override
    public CreateCurrencyResponse execute(CreateCurrencyRequest request) {
        currencyService.importCurrency(request.getCurrency());
        return new CreateCurrencyResponse(StatusCode.SUCCESS.getCode(), CREATE_CURRENCY_SUCCESSFULLY);
    }
}
