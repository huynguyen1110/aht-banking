package com.example.bank.api;

import com.example.bank.exception.StatusCode;
import com.example.bank.request.DrawMoneyRequest;
import com.example.bank.response.DrawMoneyResponse;
import com.example.bank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DrawMoneyApi extends CommonApi<DrawMoneyResponse, DrawMoneyRequest>{

    private static final String DRAW_MONEY_SUCCESS_NOTIFY = "Draw money successfully";
    private final TransactionService transactionService;

    @Override
    public DrawMoneyResponse execute(DrawMoneyRequest request) {
        transactionService.deduct(request.getSourceAccountNumber(), request.getMoney(), request.extractUserId(request.getJwt()));
        return new DrawMoneyResponse(StatusCode.SUCCESS.getCode(), DRAW_MONEY_SUCCESS_NOTIFY);
    }
}
