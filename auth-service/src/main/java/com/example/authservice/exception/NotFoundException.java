package com.example.authservice.exception;


import com.example.authservice.utils.MessagesUtils;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class NotFoundException extends RuntimeException {
    private String message;

    public NotFoundException(String errorCode, Object... var2) {
        this.message = MessagesUtils.getMessage(errorCode, var2);
    }
}