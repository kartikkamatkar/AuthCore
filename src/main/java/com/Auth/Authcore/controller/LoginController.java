package com.Auth.Authcore.controller;

import com.Auth.Authcore.Service.LoginService;
import com.Auth.Authcore.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController
{
    @Autowired
    private LoginService service;
    @PostMapping("/login")
    public String login(@RequestBody User user){
        return service.loginuser(user);}
}
