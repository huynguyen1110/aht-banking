package com.example.bank.api;

import com.example.bank.exception.StatusCode;
import com.example.bank.request.UpdateCurrencyRequest;
import com.example.bank.response.UpdateCurrencyResponse;
import com.example.bank.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateCurrencyApi extends CommonApi<UpdateCurrencyResponse, UpdateCurrencyRequest>{

    private static final String UPDATE_CURRENCY_SUCCESSFULLY = "Update currency successfully.";
    private final CurrencyService currencyService;

    @Override
    public UpdateCurrencyResponse execute(UpdateCurrencyRequest request) {
        currencyService.updateCurrency(request.getCurrency());
        return new UpdateCurrencyResponse(StatusCode.SUCCESS.getCode(), UPDATE_CURRENCY_SUCCESSFULLY);
    }
}
