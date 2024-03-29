package com.example.bank.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiRequest {

    private static final int USERID_INDEX_IN_TOKEN = 8;

    protected String token;

    public Long extractUserId(Jwt jwt){
        return (Long) jwt.getClaims()
                .values()
                .stream()
                .toList()
                .get(USERID_INDEX_IN_TOKEN);
    }
}
