package com.example.bank.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Topic {

    CREATED_ACCOUNT("created-topic"),

    CREDIT("credit-topic"),

    DEDUCT("deduct-topic"),

    TRANSFER("transfer-topic");

    private final String topic;
}
