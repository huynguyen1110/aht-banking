package com.example.apigateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ReactiveClientRegistrationRepository repository;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
        return http
                .authorizeExchange(auth -> auth
                        .pathMatchers("/health", "/actuator/health/**").permitAll()
                        .anyExchange().authenticated())
                .oauth2Login(login -> login.authenticationSuccessHandler(new CustomAuthenticationSuccessHandler()))
                .oauth2Client(Customizer.withDefaults())
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessHandler(successHandler(repository)))
                .build();
    }

    ServerLogoutSuccessHandler successHandler(ReactiveClientRegistrationRepository repository){
        OidcClientInitiatedServerLogoutSuccessHandler successHandler = new OidcClientInitiatedServerLogoutSuccessHandler(repository);
        successHandler.setPostLogoutRedirectUri("{baseUrl}/logged-out");
        return successHandler;
    }
}
