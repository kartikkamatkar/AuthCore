package com.Auth.Authcore.controller;

import com.Auth.Authcore.Service.RegisterService;
import com.Auth.Authcore.entity.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Provider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/auth")
public class RegisterController
{
    @Autowired
    private RegisterService service;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody User user ){
        System.out.println("API HIT");
        try{
            String msg = service.registerUser(user);
            return ResponseEntity.ok(msg);
        }catch(IllegalStateException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }
}
