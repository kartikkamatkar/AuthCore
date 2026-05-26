package com.Auth.Authcore.controller;

import com.Auth.Authcore.Service.EmailService;
import com.Auth.Authcore.Service.OtpService;
import com.Auth.Authcore.Service.RedisService;
import com.Auth.Authcore.Service.RegisterService;
import com.Auth.Authcore.dto.OTPVerifyRequest;
import com.Auth.Authcore.dto.ResetPasswordRequest;
import com.Auth.Authcore.entity.User;
import com.Auth.Authcore.jwt.JwtUtil;
import com.Auth.Authcore.repository.RegisterRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController
{
    @Autowired
    BCryptPasswordEncoder encoder;
    @Autowired
    private RedisService redisService;
    @Autowired
    private RegisterRepo repo;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private OtpService otpservice;
    @Autowired
    private EmailService emailService;

    @GetMapping("/stats")
    public String stats() {
        return "Admin Access Granted";
    }
    @PostMapping("/logout")
    public  String logout(HttpServletRequest request){
        String authHeader =request.getHeader("Authorization");
        String token =authHeader.substring(7);
        redisService.blacklistToken(token);
        return "LogOut ";
    }@PostMapping("/verifyotp")
public String verifyotp(@RequestBody OTPVerifyRequest request){

    String saveOTP = redisService.getOTP(request.getEmail());

    if(saveOTP == null){
        return "OTP Expired";
    }

    if(!saveOTP.equals(request.getOtp())){
        return "Invalid OTP";
    }

    User user = (User) redisService.getUser(request.getEmail());

    if(user == null){
        return "User Data Expired";
    }

    repo.save(user);

    redisService.deleteUser(request.getEmail());

    return jwtUtil.generateToken(user.getEmail());
}
    @PostMapping("/forgetpass")
    public String forgetpass(@RequestBody User user)
    {
        User dbuser=repo.findByEmail(user.getEmail()).orElse(null);
        if(dbuser==null){
            return "Email not Exist ";
        }
        String otp= otpservice.otpService();
        redisService.saveOtp(user.getEmail(),otp);
        emailService.sendOtp(user.getEmail(),otp);
        return "Reset Otp sent ";


    }
    @PostMapping("/resetpass")
    private String resetpass(@RequestBody ResetPasswordRequest request)
    {
        String saveOTP = redisService.getOTP(request.getEmail());
        if(saveOTP==null){
            return "OTP EXPIRED";
        }
        if(!saveOTP.equals(request.getOtp())){
            return "Invalid OTP ";
    }
        User user=repo.findByEmail(request.getEmail()).get();
        user.setPassword(encoder.encode(request.getNewPassword()));
        repo.save(user);
        return "Password Reset Successfully";
    }

    @org.springframework.web.bind.annotation.GetMapping("/me")
    public User me(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            return null;
        }
        String token = authHeader.substring(7);
        String email = jwtUtil.extractUsername(token);
        User user = repo.findByEmail(email).orElse(null);
        if(user!=null){
            user.setPassword(null);
        }
        return user;
    }
}
