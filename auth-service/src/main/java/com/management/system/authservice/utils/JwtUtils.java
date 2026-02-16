package com.management.system.authservice.utils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtils {

    private final Key accessSecretKey;
    private final Key refreshSecretKey;

    @Value("${jwt.accessTime}")
    private long accessTime;
    @Value("${jwt.refreshTime}")
    private long refreshTime;

    public JwtUtils(@Value("${jwt.accessSecret}") String accessSecret,
                    @Value("${jwt.refreshSecret}") String refreshSecret){
        byte[] accessByte = Base64.getDecoder().decode(accessSecret.getBytes(StandardCharsets.UTF_8));
        byte[] refreshByte = Base64.getDecoder().decode(refreshSecret.getBytes(StandardCharsets.UTF_8));

        this.accessSecretKey = Keys.hmacShaKeyFor(accessByte);
        this.refreshSecretKey = Keys.hmacShaKeyFor(refreshByte);
    }

    public String generateToken(String email, String role, boolean isRefresh){
        long expirationTime = isRefresh? refreshTime : accessTime;
        Key secretKey = isRefresh ? refreshSecretKey : accessSecretKey;
        var builder =
                Jwts.builder().subject(email).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+ expirationTime));
        if(!isRefresh){
            builder.claim("role", role);
        }
        return builder.signWith(secretKey).compact();
    }

    public void validateToken(String token, boolean isRefresh){
        Key secretKey = isRefresh ? refreshSecretKey : accessSecretKey;
        try {
            Jwts.parser().verifyWith((SecretKey) secretKey).build().parseSignedClaims(token);
        } catch (SignatureException e) {
            throw new JwtException("Invalid JWT signature");
        } catch (ExpiredJwtException e) {
            throw new JwtException("Token has expired.");
        } catch (JwtException e) {
            throw new JwtException("Invalid JWT");
        }
    }

    public String extractEmail (String token, boolean isRefresh){
        Key secretKey = isRefresh ? refreshSecretKey : accessSecretKey;
        return Jwts.parser().verifyWith((SecretKey) secretKey).build().parseSignedClaims(token).getPayload().getSubject();
    }
}
