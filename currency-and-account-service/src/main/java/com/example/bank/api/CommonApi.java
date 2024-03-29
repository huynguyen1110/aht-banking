package com.example.bank.api;

import com.example.bank.request.ApiRequest;
import com.example.bank.response.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

public abstract class CommonApi<T extends ApiResponse, U extends ApiRequest> {
    public abstract T execute(U request) throws JsonProcessingException;
}
