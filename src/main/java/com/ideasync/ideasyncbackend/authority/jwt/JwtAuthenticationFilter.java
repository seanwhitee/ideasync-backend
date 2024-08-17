package com.ideasync.ideasyncbackend.authority.jwt;
import com.ideasync.ideasyncbackend.authority.MyUser;
import com.ideasync.ideasyncbackend.authority.MyUserDetailsService;
import com.ideasync.ideasyncbackend.authority.jwt.SecurityConfig;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    private static final String HEADER_AUTH = "Authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader(HEADER_AUTH);
        if (StringUtils.hasText(authHeader)){ // 替代 ObjectUtils.isNotEmpty(authHeader)
            String accessToken = authHeader.replace("Bearer ", "");
            String username = JwtUtil.parseToken(accessToken);

            MyUser myUser = myUserDetailsService.loadUserByUsername(username);
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            username, myUser.getPassword(), myUser.getAuthorities());
            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

}
