package com.example.bank.api;

import com.example.bank.exception.StatusCode;
import com.example.bank.request.DeleteCurrencyRequest;
import com.example.bank.response.DeleteCurrencyResponse;
import com.example.bank.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteCurrencyApi extends CommonApi<DeleteCurrencyResponse, DeleteCurrencyRequest>{

    private static final String DELETE_CURRENCY_SUCCESSFULLY = "Delete currency successfully.";
    private final CurrencyService currencyService;

    @Override
    public DeleteCurrencyResponse execute(DeleteCurrencyRequest request) {
        currencyService.deleteCurrency(request.getCurrencySymbol());
        return new DeleteCurrencyResponse(StatusCode.SUCCESS.getCode(), DELETE_CURRENCY_SUCCESSFULLY);
    }
}
