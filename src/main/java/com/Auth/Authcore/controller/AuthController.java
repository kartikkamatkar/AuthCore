package com.Auth.Authcore.controller;

import com.Auth.Authcore.Service.RedisService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController
{
    @Autowired
    private RedisService redisService;
    @PostMapping("/logout")
    public  String logout(HttpServletRequest request){
        String authHeader =request.getHeader("Authorization");
        String token =authHeader.substring(7);
        redisService.blacklistToken(token);
        return "LogOut ";
    }
}
