package com.Auth.Authcore.controller;

import com.Auth.Authcore.Service.RedisService;
import com.Auth.Authcore.Service.RegisterService;
import com.Auth.Authcore.dto.OTPVerifyRequest;
import com.Auth.Authcore.entity.User;
import com.Auth.Authcore.jwt.JwtUtil;
import com.Auth.Authcore.repository.RegisterRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController
{
    @Autowired
    private RedisService redisService;
    @Autowired
    private RegisterRepo repo;
    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping("/logout")
    public  String logout(HttpServletRequest request){
        String authHeader =request.getHeader("Authorization");
        String token =authHeader.substring(7);
        redisService.blacklistToken(token);
        return "LogOut ";
    }
    @PostMapping("/verifyotp")
    public String verifyotp(@RequestBody OTPVerifyRequest request){
        String saveOTP=redisService.getOTP(request.getEmail());
        if(saveOTP==null){
            return "OTP Expire ";
        }
        if(saveOTP.equals(request.getOtp())){
            User user =repo.findByEmail(request.getEmail()).get();
            return jwtUtil.generateToken(user.getName());

        }
        return "Invalid Otp";
    }
}
