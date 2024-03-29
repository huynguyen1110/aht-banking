package com.example.authservice.mapper;

import com.example.authservice.dto.UserResponse;
import com.example.authservice.model.Role;
import com.example.authservice.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapperImpl implements UserMapper{
    @Override
    public UserResponse toUserResponse(User user) {
        List<String> roles = new ArrayList<>();
        for (Role role: user.getRoles()){
            roles.add(role.getName());
        }
        return UserResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .createdAt(user.getCreatedAt())
                .roles(roles)
                .build();
    }
}
