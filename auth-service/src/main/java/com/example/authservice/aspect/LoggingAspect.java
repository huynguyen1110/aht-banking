package com.example.authservice.aspect;

import com.example.authservice.model.EActivityType;
import com.example.authservice.model.User;
import com.example.authservice.model.UserActivityLog;
import com.example.authservice.service.UserActivityLogService;
import com.example.authservice.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationConsentAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcLogoutAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
@Configuration
@RequiredArgsConstructor
public class LoggingAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final UserActivityLogService userActivityLogService;
    private final UserDetailsServiceImpl userDetailsService;
    
    @AfterReturning("execution(* com.example.authservice.service.UserService.update(..))")
    public void logUpdateAccount(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            User user = (User) userDetailsService.loadUserByUsername(authentication.getName());
            UserActivityLog userActivityLog = UserActivityLog.builder()
                    .activityType(EActivityType.CHANGE_PROFILE)
                    .user(user)
                    .timestamp(new Date())
                    .build();
            userActivityLogService.save(userActivityLog);
            log.info("User " + user.getUsername() + " login successfully.");
        }
    }


    @AfterReturning(pointcut="execution(* org.springframework.security.authentication.AuthenticationManager.authenticate(..))", returning="result")
    public void afterLogin(Object result) {
        if(result instanceof OAuth2AuthorizationConsentAuthenticationToken) {
            User user = (User) userDetailsService.loadUserByUsername(((Authentication) result).getName());
            UserActivityLog userActivityLog = UserActivityLog.builder()
                    .activityType(EActivityType.LOGIN)
                    .user(user)
                    .timestamp(new Date())
                    .build();
            userActivityLogService.save(userActivityLog);
            log.info("User " + ((Authentication) result).getName() + " login successfully.");
        }
    }

    @AfterReturning(pointcut="execution(* org.springframework.security.authentication.AuthenticationManager.authenticate(..))", returning="result")
    public void beforeLogout(Object result) throws Throwable {
        if(result instanceof OidcLogoutAuthenticationToken) {
            User user = (User) userDetailsService.loadUserByUsername(((Authentication) result).getName());
            UserActivityLog userActivityLog = UserActivityLog.builder()
                    .activityType(EActivityType.LOGOUT)
                    .user(user)
                    .timestamp(new Date())
                    .build();
            userActivityLogService.save(userActivityLog);
            log.info("User " + ((Authentication) result).getName() + " logout successfully.");
        }
    }

}
