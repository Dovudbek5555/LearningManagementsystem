package com.example.test_system.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.token.key}")
    private String key;
    @Value("${jwt.token.ttl}")
    private Long ttl;

    public String generateToken(String phoneNumber) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(phoneNumber)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + ttl))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    public String getPhoneNumberFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
