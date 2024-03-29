package com.example.bank.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GroupId {

    ACCOUNT_GROUP("account-group");

    private final String groupId;
}
