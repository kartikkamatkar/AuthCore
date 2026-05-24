package com.Auth.Authcore.controller;

import com.Auth.Authcore.Service.LoginService;
import com.Auth.Authcore.dto.AuthResponse;
import com.Auth.Authcore.entity.RefreshToken;
import com.Auth.Authcore.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController
{
    @Autowired
    private LoginService service;

    @PostMapping("/login")
    public String login(@RequestBody User user){
        return service.loginuser(user);}
    @PostMapping("/refresh")
    public String refresh(@RequestBody RefreshToken request){
        return "Refresh Token Api Working ";
    }
}
