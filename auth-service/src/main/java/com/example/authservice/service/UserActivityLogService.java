package com.example.authservice.service;

import com.example.authservice.model.UserActivityLog;
import com.example.authservice.repository.UserActivityLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserActivityLogService {

    private final UserActivityLogRepository userActivityLogRepository;

    public void save(UserActivityLog userActivityLog){
        userActivityLogRepository.save(userActivityLog);
    }
}
