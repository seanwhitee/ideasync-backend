package com.ideasync.ideasyncbackend.authority.jwt;

import com.ideasync.ideasyncbackend.authority.jwt.JwtUtil;
import com.ideasync.ideasyncbackend.authority.MyUserDetailsService;

import org.springframework.security.core.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class JwtAuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    MyUserDetailsService myUserDetailsService;

    public String auth(Map<String, Object> reqDTO){
        String username = (String) reqDTO.get("username");
        String password = (String) reqDTO.get("password");
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(username, password);
        authentication = authenticationManager.authenticate(authentication);
        String token = JwtUtil.generateToken(authentication);

        return token;
    }
}

