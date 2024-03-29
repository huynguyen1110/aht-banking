package com.example.bank.api;

import com.example.bank.exception.StatusCode;
import com.example.bank.request.DepositRequest;
import com.example.bank.response.DepositResponse;
import com.example.bank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DepositApi extends CommonApi<DepositResponse, DepositRequest>{

    private static final String DEPOSITING_SUCCESSFULLY_NOTIFY = "Deposit successfully.";
    private final TransactionService transactionService;

    @Override
    public DepositResponse execute(DepositRequest request) {
        transactionService.credit(request.getDestinationAccountNumber(), request.getMoney());
        return new DepositResponse(StatusCode.SUCCESS.getCode(), DEPOSITING_SUCCESSFULLY_NOTIFY);
    }
}
