package com.example.bank.controller;

import com.example.bank.api.*;
import com.example.bank.dto.CreateAccountDTO;
import com.example.bank.request.*;
import com.example.bank.response.*;
import com.example.bank.validation.AccountNumberValidation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Validated
public class AccountController {

    private final AccountCreationApi accountCreationApi;
    private final AccountDetailFetchingApi accountDetailFetchingApi;
    private final UpdateAccountApi updateAccountApi;
    private final DeleteAccountApi deleteAccountApi;
    private final AllUserAccountFetchingApi allUserAccountFetchingApi;
    private final FetchingDestinationUserApi fetchingDestinationUserApi;

    @PostMapping("/create")
    public AccountCreationResponse createAccount(@RequestBody @Valid CreateAccountDTO createAccountDTO,
                                                 @RequestHeader("Authorization") String token) {
        String bearerToken = token.substring("Bearer ".length());
        AccountCreationRequest accountCreationRequest = new AccountCreationRequest(createAccountDTO, bearerToken);
        return accountCreationApi.execute(accountCreationRequest);
    }

    @GetMapping("/detail/{accountNumber}")
    public AccountDetailFetchingResponse getAccountDetail(@PathVariable @AccountNumberValidation @NotBlank(message = "Account Number can not be blank.") String accountNumber) {
        AccountDetailFetchingRequest accountDetailFetchingRequest = new AccountDetailFetchingRequest(accountNumber);
        return accountDetailFetchingApi.execute(accountDetailFetchingRequest);
    }

    @PutMapping("/update")
    public UpdateAccountResponse updateAccount(@RequestParam @Valid Long accountId,
                                               @RequestParam @AccountNumberValidation @NotBlank(message = "Account Number can not be blank.") String newAccountNumber,
                                               @RequestHeader("Authorization") String token) {
        String bearerToken = token.substring("Bearer ".length());
        UpdateAccountRequest updateAccountRequest = new UpdateAccountRequest(accountId, newAccountNumber, bearerToken);
        return updateAccountApi.execute(updateAccountRequest);
    }

    @DeleteMapping("/delete")
    public DeleteAccountResponse deleteAccount(@RequestParam @AccountNumberValidation @NotBlank(message = "Account Number can not be blank.") String accountNumber,
                                               @RequestHeader("Authorization") String token) {
        String bearerToken = token.substring("Bearer ".length());
        DeleteAccountRequest deleteAccountRequest = new DeleteAccountRequest(accountNumber, bearerToken);
        return deleteAccountApi.execute(deleteAccountRequest);
    }

    @GetMapping("/get-all-user-account")
    public AllAccountFetchingResponse getAllUserAccount(){
        AllAccountFetchingRequest request = new AllAccountFetchingRequest();
        return allUserAccountFetchingApi.execute(request);
    }

    @GetMapping("/get-username-account/{destinationAccountNumber}")
    public UserInfoResponse getDestinationUserInfo(@PathVariable @AccountNumberValidation @NotBlank(message = "Account Number can not be blank.") String destinationAccountNumber) {
        DestinationAccountRequest request = new DestinationAccountRequest(destinationAccountNumber);
        return fetchingDestinationUserApi.execute(request);
    }
}
