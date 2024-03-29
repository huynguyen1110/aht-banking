package com.example.bank.controller;

import com.example.bank.api.CreateAccountApi;
import com.example.bank.api.DeleteAccountApi;
import com.example.bank.api.UpdateAccountApi;
import com.example.bank.model.Account;
import com.example.bank.request.CreateAccountRequest;
import com.example.bank.request.DeleteAccountRequest;
import com.example.bank.request.UpdateAccountRequest;
import com.example.bank.response.CreateAccountResponse;
import com.example.bank.response.DeleteAccountResponse;
import com.example.bank.response.UpdateAccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Validated
public class AccountController {

    private final CreateAccountApi createAccountApi;
    private final DeleteAccountApi deleteAccountApi;
    private final UpdateAccountApi updateAccountApi;

    @PostMapping("/create")
    public CreateAccountResponse createAccount(@RequestBody Account account) {
        CreateAccountRequest createAccountRequest = new CreateAccountRequest(account);
        return createAccountApi.execute(createAccountRequest);
    }

    @DeleteMapping("/delete")
    public DeleteAccountResponse deleteAccount(@RequestParam("accountNumber") String accountNumber) {
        DeleteAccountRequest deleteAccountRequest = new DeleteAccountRequest(accountNumber);
        return deleteAccountApi.execute(deleteAccountRequest);
    }

    @PutMapping("/update")
    public UpdateAccountResponse updateAccount(@RequestParam("accountId") Long accountId,
                                               @RequestParam("accountNumber") String accountNumber) {
        UpdateAccountRequest updateAccountRequest = new UpdateAccountRequest(accountId, accountNumber);
        return updateAccountApi.execute(updateAccountRequest);
    }

}
