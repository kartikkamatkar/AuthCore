package com.Auth.Authcore.controller;

import com.Auth.Authcore.Service.RegisterService;
import com.Auth.Authcore.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Provider;

@RestController
@RequestMapping("/auth")
public class RegisterController
{
    @Autowired
    private RegisterService service;
    @PostMapping("/register")
    public  String register(@RequestBody User user ){
        System.out.println("API HIT");
        return  service.registerUser(user);
    }
}
