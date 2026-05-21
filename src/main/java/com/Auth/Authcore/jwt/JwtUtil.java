package com.Auth.Authcore.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.xml.crypto.Data;
import java.security.Signature;
import java.util.Date;

public class JwtUtil
{
    public final String SECRET_KEY="mysecretkeymysecretkeymysecretkey";
    public String generateToken(String name){
        return Jwts.builder()
                .setSubject(name)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis()
                                +1000*60*60)
                        )
                                .signWith(
                                        SignatureAlgorithm.HS256,
                                        SECRET_KEY
                                )
                                .compact();

    }
    public String extractuname(String token){
        Claims claims=
                Jwts.parser().setSigningKey(SECRET_KEY)
                        .parseClaimsJws(token)
                        .getBody();
        return claims.getSubject();
    }
}
