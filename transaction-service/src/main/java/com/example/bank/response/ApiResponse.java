package com.example.bank.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {

    private int code;

    private String message;
}
