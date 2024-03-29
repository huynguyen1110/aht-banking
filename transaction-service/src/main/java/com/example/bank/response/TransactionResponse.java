package com.example.bank.response;

import com.example.bank.model.Transaction;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class TransactionResponse extends ApiResponse{

    private Page<Transaction> transaction;

    public TransactionResponse(int code, String message) {
        super(code, message);
    }

    public TransactionResponse(Page<Transaction> transaction){
        super();
        this.transaction = transaction;
    }

}
