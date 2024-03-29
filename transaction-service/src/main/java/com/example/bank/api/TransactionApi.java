package com.example.bank.api;

import com.example.bank.model.Transaction;
import com.example.bank.request.TransactionRequest;
import com.example.bank.response.TransactionResponse;
import com.example.bank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionApi extends CommonApi<TransactionResponse, TransactionRequest>{

    private static final Integer PAGE_SIZE = 10;
    private final TransactionService transactionService;

    @Override
    public TransactionResponse execute(TransactionRequest request) {
        Page<Transaction> transactions = transactionService.getAllTransactionOfAnUser(request.extractUserId(request.getJwt()),
                request.getPageNumber(),
                PAGE_SIZE);
        return new TransactionResponse(transactions);
    }
}
