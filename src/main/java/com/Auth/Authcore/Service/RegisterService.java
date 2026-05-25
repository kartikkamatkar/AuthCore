package com.Auth.Authcore.Service;

import com.Auth.Authcore.entity.User;
import com.Auth.Authcore.repository.RegisterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService
{
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private RegisterRepo repo;
    @Autowired
    private EmaiService emaiService;
    @Autowired
    private OtpService otpService;

    @Autowired
    private RedisService redisService;
    public String registerUser(User user){
        if(repo.existsByEmail(user.getEmail())){
            return "Email is Already Exist";
        }
        String otp = otpService.otpService();
        redisService.saveOtp(user.getEmail(),otp);
        emaiService.sendOtp(user.getEmail(),otp);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("USER");
        repo.save(user);
        System.out.println("Saved ");
        return "Registration Successfully Otp Sent Successfully :";
    }

}
