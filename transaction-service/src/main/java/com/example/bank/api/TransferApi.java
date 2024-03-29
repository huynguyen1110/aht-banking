package com.example.bank.api;

import com.example.bank.exception.StatusCode;
import com.example.bank.request.TransferRequest;
import com.example.bank.response.TransferResponse;
import com.example.bank.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@RequiredArgsConstructor
public class TransferApi extends CommonApi<TransferResponse, TransferRequest>{

    private static final String EXCHANGE_SUCCESSFULLY_NOTIFY = "Transfer successfully.";
    private final TransactionService transactionService;

    @Override
    public TransferResponse execute(TransferRequest request) {
        for(int i = 0; i < 1000; i++) {
            transactionService.transfer(request.getSourceAccountNumber(), request.getDestinationAccountNumber(), request.getMoney(), request.extractUserId(request.getJwt()));
        }
        return new TransferResponse(StatusCode.SUCCESS.getCode(), EXCHANGE_SUCCESSFULLY_NOTIFY);
    }
}
