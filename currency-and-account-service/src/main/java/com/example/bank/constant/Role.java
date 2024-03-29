package com.example.bank.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {

    USER("USER"),

    ADMIN("ADMIN"),

    STAFF("STAFF");

    private final String role;
}
