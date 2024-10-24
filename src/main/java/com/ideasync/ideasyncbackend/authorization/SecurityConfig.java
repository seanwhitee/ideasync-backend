package com.ideasync.ideasyncbackend.authorization;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;
import java.util.List;


@Configuration
public class SecurityConfig {
    private final AuthorizationService authorizationService;

    public SecurityConfig(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(authorize ->
                        // Here we set authentication for all endpoints
                        authorize.requestMatchers("/api/v1/users/sendEmail",
                                        "/api/v1/users/login",
                                        "/api/v1/users/saveUserData",
                                        "/api/v1/users/checkUserData",
                                        "/api/v1/authorization/generateToken").permitAll()
                                .anyRequest().authenticated()
                )
                // Here we enable that we will accept JWTs
                .oauth2ResourceServer(configure -> configure.jwt(Customizer.withDefaults()))
                .build();
    }

    public OAuth2TokenValidator<Jwt> tokenValidator() {
        final List<OAuth2TokenValidator<Jwt>> validators =
                List.of(new JwtTimestampValidator(),
                        new JwtIssuerValidator("https://ideasync.withpaipai.com")
                );
        return new DelegatingOAuth2TokenValidator<>(validators);
    }

    @Bean
    public JwtDecoder jwtDecoder() throws Exception {
        final NimbusJwtDecoder decoder = NimbusJwtDecoder
                .withPublicKey(authorizationService.getPublicKey())
                .build();
        decoder.setJwtValidator(tokenValidator());
        return decoder;
    }
}

