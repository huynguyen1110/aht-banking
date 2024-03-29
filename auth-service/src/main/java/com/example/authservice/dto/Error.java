package com.example.authservice.dto;

public record Error(String statusCode, String title, String detail) {
}
