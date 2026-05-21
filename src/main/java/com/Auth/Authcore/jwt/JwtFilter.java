package com.Auth.Authcore.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.annotations.CollectionTypeRegistration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter
{
    private  JwtUtil jwtUtil=new JwtUtil();

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    )
    throws ServletException,IOException{
    String authHeader=request.getHeader("Authorization");
    if(authHeader!=null&& authHeader.startsWith("Bearer ")){
        String token=authHeader.substring(7);
        String username=jwtUtil.extractuname(token);
        System.out.println(username);
        }
    filterChain.doFilter(request,response);
    }
}
