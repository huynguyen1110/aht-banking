package com.example.authservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EActivityType {

    LOGIN("Login"),

    LOGOUT("Logout"),

    CHANGE_PROFILE("Change Profile");

    private final String name;
}
