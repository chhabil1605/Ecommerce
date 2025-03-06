package com.ecommerce.user_service.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtService {

    @Autowired
    private Environment env;

    public String getSecret() {
        return env.getProperty("SECRET");
    }


/**
 * Generates a JSON Web Token (JWT) for the given email and userId.
 *
 * This function creates a JWT with claims containing the userId and sets the subject as the user's email.
 * The token is issued at the current time and expires in 30 minutes.
 * It is signed using the HS256 algorithm and a secret key.
 *
 * @param email the email of the user for whom the token is being generated
 * @param userId the unique identifier of the user
 * @return a signed JWT token as a String
 */
    public String generateJwtToken(String email,Long userId) {
        Map<String,Object> claims=new HashMap<>();
        claims.put("userId",userId);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    private Key getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode(getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserId(String token){
        Claims claims=Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return (String) claims.get("userId");
    }

    public String extractEmail(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .toString();
    }

    public boolean isTokenExpired(String token){
        return extractClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token,String email){
        return (email.equals(extractEmail(token)) && !isTokenExpired(token));
    }


    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)

                .getBody();
    }
}
