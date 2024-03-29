package com.example.bank.response;

import com.example.bank.dto.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoResponse extends ApiResponse{

    UserResponseDTO userResponseDTO;

    public UserInfoResponse(int code, String message, UserResponseDTO userResponseDTO) {
        super(code, message);
        this.userResponseDTO = userResponseDTO;
    }

    public UserInfoResponse(int code, String message) {
        super(code, message);
    }
}
