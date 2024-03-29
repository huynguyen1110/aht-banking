package com.example.bank.controller;

import com.example.bank.api.CreateCurrencyApi;
import com.example.bank.api.DeleteCurrencyApi;
import com.example.bank.api.FetchCurrencyApi;
import com.example.bank.api.UpdateCurrencyApi;
import com.example.bank.constant.CurrencySymbol;
import com.example.bank.model.Currency;
import com.example.bank.request.*;
import com.example.bank.response.CreateCurrencyResponse;
import com.example.bank.response.DeleteCurrencyResponse;
import com.example.bank.response.FetchCurrencyResponse;
import com.example.bank.response.UpdateCurrencyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/currency")
public class CurrencyController {

    private final UpdateCurrencyApi updateCurrencyApi;
    private final CreateCurrencyApi createCurrencyApi;
    private final DeleteCurrencyApi deleteCurrencyApi;
    private final FetchCurrencyApi fetchCurrencyApi;

    @PostMapping("/create")
    public CreateCurrencyResponse createCurrency(@RequestBody Currency currency) {
        CreateCurrencyRequest createCurrencyRequest = new CreateCurrencyRequest(currency);
        return createCurrencyApi.execute(createCurrencyRequest);
    }

    @PutMapping("/update")
    public UpdateCurrencyResponse updateCurrency(@RequestBody Currency currency) {
        UpdateCurrencyRequest updateCurrencyRequest = new UpdateCurrencyRequest(currency);
        return updateCurrencyApi.execute(updateCurrencyRequest);
    }

    @GetMapping("/{currencySymbol}")
    public FetchCurrencyResponse fetchCurrency(@PathVariable CurrencySymbol currencySymbol) {
        FetchCurrencyRequest fetchCurrencyRequest = new FetchCurrencyRequest(currencySymbol);
        return fetchCurrencyApi.execute(fetchCurrencyRequest);
    }

    @DeleteMapping("/delete/{currencySymbol}")
    public DeleteCurrencyResponse deleteCurrency(@PathVariable CurrencySymbol currencySymbol) {
        DeleteCurrencyRequest deleteCurrencyRequest = new DeleteCurrencyRequest(currencySymbol);
        return deleteCurrencyApi.execute(deleteCurrencyRequest);
    }
}
