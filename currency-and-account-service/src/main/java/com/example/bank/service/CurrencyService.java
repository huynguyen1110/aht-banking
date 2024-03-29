package com.example.bank.service;

import com.example.bank.constant.CurrencySymbol;
import com.example.bank.exception.AppException;
import com.example.bank.exception.ErrorCode;
import com.example.bank.model.Currency;
import com.example.bank.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public Currency importCurrency(Currency currency) {
        return currencyRepository.save(currency);
    }

    public List<Currency> getCurrenciesList() {
        return currencyRepository.findAll();
    }

    public void deleteCurrency(CurrencySymbol currencySymbol) {
        currencyRepository.deleteBySymbolEquals(currencySymbol);
    }

    public void updateCurrency(Currency currency) {
        if(!currencyRepository.existsById(currency.getCurrencyCode())) {
            throw new AppException(ErrorCode.CURRENCY_NOT_FOUND);
        }
        currencyRepository.save(currency);
    }

    public Currency getCurrency(CurrencySymbol currencySymbol) {
        return currencyRepository.findCurrencyBySymbolEquals(currencySymbol);
    }
}
