package com.ideasync.ideasyncbackend.authorization;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/authorization")
@CrossOrigin
public class AuthorizationController {
    @GetMapping("/token")
    public Token getAccessToken(JwtAuthenticationToken jwtToken) {
        return new Token(
                jwtToken.getToken(),
                jwtToken.getAuthorities()
        );
    }
    public record Token(Jwt token, Collection<GrantedAuthority> authorities) {}
}
