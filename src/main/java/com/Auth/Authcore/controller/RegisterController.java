package com.Auth.Authcore.controller;

import com.Auth.Authcore.Service.RegisterService;
import com.Auth.Authcore.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Provider;

@RestController
public class RegisterController
{
    @Autowired
    private RegisterService service;
    @PostMapping("/register")
    public  String register(@RequestBody User user ){
        return  service.registerUser(user);
    }
}
