package com.example.bank.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public enum Topic {

    CREDIT("credit-topic"),

    DEDUCT("deduct-topic"),

    CREATED_ACCOUNT("created-account"),

    TRANSFER("transfer-topic");

    private final String topic;
}
