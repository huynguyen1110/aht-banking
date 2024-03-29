package com.example.authservice.exception;

public class UserCanNotFoundException extends Exception {

    public UserCanNotFoundException() {
        super("User with this username not found in database !!");
    }

    public UserCanNotFoundException(String msg) {
        super(msg);
    }
}
