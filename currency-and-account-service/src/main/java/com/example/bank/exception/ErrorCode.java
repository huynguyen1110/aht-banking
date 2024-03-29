package com.example.bank.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_PARAMETER_DATA(998, null),

    PARAMETER_NOT_VALID(999, null),

    INVALID_PARAMETER_TYPE(1000, "Parameter type is not suitable."),

    ACCOUNT_NOT_FOUND(1001, "Account has not been registered."),

    CURRENCY_NOT_FOUND(1003, "There is not this currency in system."),

    ACCOUNT_ALREADY_EXISTED(1004, "Account has been registered."),

    CURRENCY_ALREADY_EXISTED(1005, "Currency has been used."),

    ACCOUNT_NOT_FILLED(1006, "Please fill the account information."),

    INVALID_ACCOUNT_NUMBER(1007, "Please fill the account number."),

    USER_NOT_FOUND(1008, "User has not been registered"),

    REQUEST_TIMEOUT(1009, "Request Timeout or Server Overloading."),

    USER_ACCOUNT_NOT_FOUND(1010, "User does not have account in the system.");

    private final int code;

    private final String message;

}
