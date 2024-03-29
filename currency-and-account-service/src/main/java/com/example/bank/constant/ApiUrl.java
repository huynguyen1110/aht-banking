package com.example.bank.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApiUrl {

    AUTH_SERVICE_HOST("http://auth-service/");

    private final String url;
}
