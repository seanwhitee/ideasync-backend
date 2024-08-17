package com.ideasync.ideasyncbackend.authority.jwt;


import com.ideasync.ideasyncbackend.authority.MyUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.JwtParser;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Calendar;

@Component
public class JwtUtil {
    private static final String ISS = "Bearer";
    private static final String SECRET = "Authorization";
    private static final int EXPIRE_TIME = 30;

    public static String generateToken(Authentication authentication) {
        MyUser myUser = (MyUser) authentication.getPrincipal();
        Calendar exp = Calendar.getInstance();
        exp.add(Calendar.MINUTE, EXPIRE_TIME);

        Claims claims = Jwts.claims();
        claims.setSubject(myUser.getUsername());
        claims.setExpiration(exp.getTime());
        claims.setIssuer(ISS);
        Key secretKey = Keys.hmacShaKeyFor(SECRET.getBytes());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(secretKey)
                .compact(); // 將 JwtBuilder 構建的 JWT 物件，壓縮為一個字串的形式
    }
    public static String parseToken(String token){
        try{
            Key secretKey = Keys.hmacShaKeyFor(SECRET.getBytes());

            JwtParser parser = Jwts.parserBuilder()
                    .setSigningKey(secretKey).build();

            Claims claims = parser.parseClaimsJws(token).getBody();
            String userName = claims.getSubject();

            return userName;
        }catch (JwtException e) {//如果taken有問題，會有jwtException
            System.out.println("Invalid JWT: unauthorized");
            return null;
        }

    }
}





