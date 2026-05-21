package com.Auth.Authcore.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil
{
    private static final String SECRET_KEY =
            "myverysecuresecretkeymyverysecuresecretkey123";

    private final Key key =
            Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(String username)
    {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + 1000 * 60 * 60)
                )
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token)
    {
        return extractClaims(token).getSubject();
    }

    public Claims extractClaims(String token)
    {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(
            String token,
            UserDetails userDetails
    )
    {
        String username = extractUsername(token);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token)
    {
        return extractClaims(token)
                .getExpiration()
                .before(new Date());
    }
}