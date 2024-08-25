package com.ideasync.ideasyncbackend.authorization;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPublicKey;
import java.util.List;


@Configuration
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.public-key-location}")
    private RSAPublicKey key;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(authorize ->
                        // Here we set authentication for all endpoints
                        authorize.anyRequest().authenticated()
                )
                // Here we enable that we will accept JWTs
                .oauth2ResourceServer(configure -> configure.jwt(Customizer.withDefaults()))
                .build();
    }

    public OAuth2TokenValidator<Jwt> audienceValidator() {
        return new JwtClaimValidator<List<String>>(
                OAuth2TokenIntrospectionClaimNames.AUD,
                aud -> aud.contains("foobar")
        );
    }

    public OAuth2TokenValidator<Jwt> tokenValidator() {
        final List<OAuth2TokenValidator<Jwt>> validators =
                List.of(new JwtTimestampValidator(),
                        new JwtIssuerValidator("http://foobar.com"),
                        audienceValidator());
        return new DelegatingOAuth2TokenValidator<>(validators);
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        final NimbusJwtDecoder decoder = NimbusJwtDecoder
                .withPublicKey(this.key)
                .build();
        decoder.setJwtValidator(tokenValidator());
        return decoder;
    }
}

