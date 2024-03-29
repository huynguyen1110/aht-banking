package com.example.authservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ResponseBody;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ResponseBody
public class ApiResponse {
    private int code;
    private String message;
}